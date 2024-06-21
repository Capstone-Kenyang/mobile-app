package com.example.kenyang.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenyang.R
import com.example.kenyang.converter.getImageUri
import com.example.kenyang.ui.adapter.MenuAdapter
import com.example.kenyang.converter.sortListByDistance
import com.example.kenyang.converter.sortListByRating
import com.example.kenyang.data.MenuRepository
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.data.local.OrderDatabase
import com.example.kenyang.databinding.ActivityMainBinding
import com.example.kenyang.ui.fragments.BottomNavFragment
import com.example.kenyang.ui.fragments.MenuDetailFragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.BuildConfig
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private var menus = MenuRepository().getAllMenus()
    private lateinit var auth: FirebaseAuth
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var secondAdapter: MenuAdapter

    private var currentImageUri: Uri? = null
    private val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(
        Date()
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        val firebaseUser = auth.currentUser


        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getMyLastLocation()
        createLocationRequest()
        createLocationCallback()

        menuAdapter = MenuAdapter()

        val firstName = firebaseUser.displayName?.split(" ")?.get(0) ?: "User"
        binding.tvUserFirstName.text = resources.getString(R.string.greeting_message, firstName)

        binding.btnSignOut.setOnClickListener {
            signOut()
        }

        menuAdapter.submitList(sortListByDistance(menus.subList(0, 5)))

        binding.rvRecommendation.adapter = menuAdapter
        binding.rvRecommendation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        secondAdapter = MenuAdapter()
        secondAdapter.submitList(sortListByRating(menus.subList(0, 5)))
        binding.rvSecondRecommendation.adapter = secondAdapter
        binding.rvSecondRecommendation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.poster.setOnClickListener {
            val bottomSheet = MenuDetailFragment.newInstance(menus[5])
            bottomSheet.show((this as AppCompatActivity).supportFragmentManager, MenuDetailFragment.TAG)
        }

        binding.bottomNavigation.selectedItemId = R.id.menu_home
        handleBottomNavAction()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getMyLastLocation()
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    getMyLastLocation()
                }
                else -> {
                }
            }
        }

    private val resolutionLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            when (result.resultCode) {
                RESULT_OK ->
                    Log.i("Main", "onActivityResult: All location settings are satisfied.")
                RESULT_CANCELED ->
                    Toast.makeText(
                        this,
                        "Anda harus mengaktifkan GPS untuk menggunakan aplikasi ini!",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createLocationRequest() {
        val priority = Priority.PRIORITY_HIGH_ACCURACY
        val interval = TimeUnit.SECONDS.toMillis(1)
        val maxWaitTime = TimeUnit.SECONDS.toMillis(1)
        locationRequest = LocationRequest.Builder(
            priority,
            interval
        ).apply {
            setMaxUpdateDelayMillis(maxWaitTime)
        }.build()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        client.checkLocationSettings(builder.build())
            .addOnSuccessListener {
                getMyLastLocation()
            }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        resolutionLauncher.launch(
                            IntentSenderRequest.Builder(exception.resolution).build()
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        Toast.makeText(this, sendEx.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun startLocationUpdates() {
        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (exception: SecurityException) {
            Log.e("Main", "Error : " + exception.message)
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getMyLastLocation() {
        if     (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ){
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    updateMenuDistances(location)
//                    val locationAddress = getAddressFromLocation(location, this)
//                    binding.tvTagline.text = locationAddress
                } else {
                    Toast.makeText(
                        this,
                        "Location is not found. Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation
                for (location in locationResult.locations) {
                    Log.d("Main", "onLocationResult: " + location.latitude + ", " + location.longitude)
                    updateMenuDistances(location)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    private fun updateMenuDistances(currentLocation: Location) {
        val updatedMenuList = mutableListOf<Menu>()
        for (menu in menus) {
            val menuLocation = Location("").apply {
                latitude = menu.lat
                longitude = menu.lon
            }
            Log.d("Main", "location $menuLocation")
            Log.d("Main", "current $currentLocation")

            val updatedMenu = menu.copy(distance = currentLocation.distanceTo(menuLocation).toDouble())
            updatedMenuList.add(updatedMenu)
        }
        menuAdapter.submitList(sortListByDistance(updatedMenuList.subList(0, 5)))
        secondAdapter.submitList(sortListByRating(updatedMenuList.subList(0, 5)))
    }


    private fun getAddressFromLocation(location: Location, context: Context): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        if (addresses!!.isNotEmpty()) {
            val address = addresses[0]
            val city = address.locality
            val kecamatan = address.subLocality

            return if (!kecamatan.isNullOrEmpty()) "$kecamatan, $city" else city
        } else {
            return resources.getString(R.string.tagline)
        }
    }

    private fun signOut() {
        lifecycleScope.launch {
            val credentialManager = CredentialManager.create(this@MainActivity)

            auth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun handleBottomNavAction() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    // Replace with HomeFragment
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    item.setChecked(true)
                    true
                }
                R.id.menu_camera -> {
                    // Launch Camera
                    startCamera()
                    item.setChecked(false)
                    true
                }
                R.id.menu_order -> {
                    val intent = Intent(this, OrderListActivity::class.java)
                    startActivity(intent)
                    item.setChecked(true)
                    true
                }
                // Add other navigation items here
                else -> false
            }
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            val intent = Intent(this, ScanActivity::class.java)
            intent.putExtra("imageUri", currentImageUri.toString())
            startActivity(intent)
        }
    }

    private fun getImageUri(context: Context): Uri {
        var uri: Uri? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyCamera/")
            }
            uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
        }
        return uri ?: getImageUriForPreQ(context)
    }

    private fun getImageUriForPreQ(context: Context): Uri {
        val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(filesDir, "/MyCamera/$timeStamp.jpg")
        if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()
        return FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            imageFile
        )
    }

    override fun onRestart() {
        super.onRestart()
        binding.bottomNavigation.selectedItemId = R.id.menu_home
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}