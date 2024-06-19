package com.example.kenyang.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenyang.R
import com.example.kenyang.ui.adapter.CategoryMenuAdapter
import com.example.kenyang.converter.sortListByDistance
import com.example.kenyang.converter.sortListByRating
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.databinding.ActivityCategoryListBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class CategoryListActivity : AppCompatActivity() {

    private val binding: ActivityCategoryListBinding by lazy {
        ActivityCategoryListBinding.inflate(layoutInflater)
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var menuAdapter: CategoryMenuAdapter
    private lateinit var menus: List<Menu>
    private lateinit var menusUpdated: List<Menu>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val menusArrayList = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableArrayListExtra(EXTRA_MENUS, Menu::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra(EXTRA_MENUS)
        }
        menus = menusArrayList!!.toList()

        val categoryName = intent.getStringExtra(EXTRA_NAME)
        binding.tvCategoryName.text = categoryName

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        menuAdapter = CategoryMenuAdapter()
        getMyLastLocation()

        menuAdapter.submitList(menus)
        binding.rvCategoryMenuList.adapter = menuAdapter
        binding.rvCategoryMenuList.layoutManager = LinearLayoutManager(this)

//        val adapter = CategoryMenuAdapter()
//        binding.rvCategoryMenuList.adapter = adapter
//        binding.rvCategoryMenuList.layoutManager = LinearLayoutManager(this)
//        binding.tvCategoryName.text = categoryName
//        adapter.submitList(menus)


        binding.btnSortByRating.setOnClickListener {
            binding.btnSortByRating.setTextColor(resources.getColor(R.color.blue))
            binding.btnSortByRating.backgroundTintList = ContextCompat.getColorStateList(this, R.color.indigo_50)
            binding.btnSortByRating.typeface = ResourcesCompat.getFont(this, R.font.lato_bold)

            binding.btnSortByDistance.setTextColor(resources.getColor(R.color.neutral_600))
            binding.btnSortByDistance.backgroundTintList = ContextCompat.getColorStateList(this, R.color.black_3)
            binding.btnSortByDistance.typeface = ResourcesCompat.getFont(this, R.font.lato_regular)

            menuAdapter.submitList(sortListByRating(menusUpdated.toList()))
        }

        binding.btnSortByDistance.setOnClickListener {
            binding.btnSortByDistance.setTextColor(resources.getColor(R.color.blue))
            binding.btnSortByDistance.backgroundTintList = ContextCompat.getColorStateList(this, R.color.indigo_50)
            binding.btnSortByDistance.typeface = ResourcesCompat.getFont(this, R.font.lato_bold)

            binding.btnSortByRating.setTextColor(resources.getColor(R.color.neutral_600))
            binding.btnSortByRating.backgroundTintList = ContextCompat.getColorStateList(this, R.color.black_3)
            binding.btnSortByRating.typeface = ResourcesCompat.getFont(this, R.font.lato_regular)

            menuAdapter.submitList(sortListByDistance(menusUpdated.toList()))

        }

        binding.backButton.setOnClickListener {
            finish()
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
        if     (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) ||
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ){
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    updateMenuDistances(location)
                } else {
                    Toast.makeText(
                        this,
                        "Location is not found. Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(this, "Akses lokasi dibutuhkan", Toast.LENGTH_LONG).show()
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
            menusUpdated = updatedMenuList
        }
        menuAdapter.submitList(sortListByDistance(updatedMenuList))
    }

    companion object {
        const val EXTRA_MENUS = "extra-menus"
        const val EXTRA_NAME = "extra-name"
    }
}
