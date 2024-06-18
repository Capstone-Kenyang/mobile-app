package com.example.kenyang.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenyang.R
import com.example.kenyang.ui.adapter.MenuAdapter
import com.example.kenyang.converter.sortListByDistance
import com.example.kenyang.converter.sortListByRating
import com.example.kenyang.data.MenuRepository
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.data.local.OrderDatabase
import com.example.kenyang.databinding.ActivityMainBinding
import com.example.kenyang.ui.fragments.MenuDetailFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private var menus = MenuRepository().getAllMenus()
    private lateinit var auth: FirebaseAuth
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var secondAdapter: MenuAdapter


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

        val orderDatabase = OrderDatabase.getDatabase(this)
        val orderDao = orderDatabase.orderDao()

//        lifecycleScope.launch {
//            try {
//                orderDao.deleteAllOrders()
//                Log.d("MainActivity", "Semua data berhasil dihapus.")
//            } catch (e: Exception) {
//                Log.e("MainActivity", "Gagal menghapus data: ${e.message}")
//            }
//        }

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
                    val locationAddress = getAddressFromLocation(location, this)
                    binding.tvTagline.text = locationAddress
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

    fun makeList(): List<Menu> {
        return listOf(
            Menu(
                menuId = "MN001",
                imageId = R.drawable.a1,
                menu = "Nasi Goreng",
                restaurant = "Warung Mak Nyak",
                restaurantId = 101,
                restaurantAddress = "Jl. Mayor Salim Batubara No. 18, Palembang",
                lat = -2.990934,
                lon = 104.742344,
                stock = 20,
                rating = 4.5,
                price = 15000,
                expireDate = "27 Mei 2025"
            ),
            Menu(
                menuId = "MN002",
                imageId = R.drawable.a2,
                menu = "Pempek Kapal Selam",
                restaurant = "Pempek Pak Raden",
                restaurantId = 102,
                restaurantAddress = "Jl. Demang Lebar Daun No. 5, Palembang",
                lat = -2.974945,
                lon = 104.742887,
                stock = 15,
                rating = 4.8,
                price = 25000,
                expireDate = "27 Mei 2025"// 25 December 2024
            ),
            Menu(
                menuId = "MN003",
                imageId = R.drawable.a3,
                menu = "Mie Ayam",
                restaurant = "Mie Ayam Jago",
                restaurantId = 103,
                restaurantAddress = "Jl. Pangeran Antasari No. 10, Palembang",
                lat = -2.9765,
                lon = 104.7345,
                stock = 25,
                rating = 4.3,
                price = 12000,
                expireDate = "27 Mei 2025" // 10 January 2025
            ),
            Menu(
                menuId = "MN004",
                imageId = R.drawable.a4,
                menu = "Sate Padang",
                restaurant = "Sate Padang Ajo",
                restaurantId = 104,
                restaurantAddress = "Jl. Kapt. A. Rivai No. 2, Palembang",
                lat = -2.9867,
                lon = 104.7389,
                stock = 10,
                rating = 4.6,
                price = 30000,
                expireDate = "27 Mei 2025" // 20 December 2024
            ),
            Menu(
                menuId = "MN005",
                imageId = R.drawable.a5,
                menu = "Ayam Bakar",
                restaurant = "Ayam Bakar Wong Solo",
                restaurantId = 105,
                restaurantAddress = "Jl. Basuki Rahmat No. 8, Palembang",
                lat = -2.9932,
                lon = 104.7396,
                stock = 30,
                rating = 4.7,
                price = 35000,
                expireDate = "27 Mei 2025"// 5 February 2025
            )
        )
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

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}