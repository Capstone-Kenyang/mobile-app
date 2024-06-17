package com.example.kenyang.ui.activity

import ItemMenuAdapter
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kenyang.R
import com.example.kenyang.ui.adapter.CategoryMenuAdapter
import com.example.kenyang.ui.adapter.MenuAdapter
import com.example.kenyang.converter.sortListByDistance
import com.example.kenyang.converter.sortListByRating
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.databinding.ActivityCategoryListBinding
import com.example.kenyang.ui.viewmodel.CategoryListViewModel
import com.example.kenyang.ui.viewmodel.MenuItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class CategoryListActivity : AppCompatActivity() {

    private val binding: ActivityCategoryListBinding by lazy {
        ActivityCategoryListBinding.inflate(layoutInflater)
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var menuAdapter: CategoryMenuAdapter
    private lateinit var menus: List<Menu>

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocation()

        menuAdapter = CategoryMenuAdapter()

        val adapter = CategoryMenuAdapter()
        binding.rvCategoryMenuList.adapter = adapter
        binding.rvCategoryMenuList.layoutManager = LinearLayoutManager(this)
        binding.tvCategoryName.text = categoryName
        adapter.submitList(menus)


        binding.btnSortByRating.setOnClickListener {
            binding.btnSortByRating.setTextColor(resources.getColor(R.color.blue))
            binding.btnSortByRating.backgroundTintList = ContextCompat.getColorStateList(this, R.color.indigo_50)
            binding.btnSortByRating.typeface = ResourcesCompat.getFont(this, R.font.lato_bold)

            binding.btnSortByDistance.setTextColor(resources.getColor(R.color.neutral_600))
            binding.btnSortByDistance.backgroundTintList = ContextCompat.getColorStateList(this, R.color.black_3)
            binding.btnSortByDistance.typeface = ResourcesCompat.getFont(this, R.font.lato_regular)

            adapter.submitList(sortListByRating(menus!!.toList()))
        }

        binding.btnSortByDistance.setOnClickListener {
            binding.btnSortByDistance.setTextColor(resources.getColor(R.color.blue))
            binding.btnSortByDistance.backgroundTintList = ContextCompat.getColorStateList(this, R.color.indigo_50)
            binding.btnSortByDistance.typeface = ResourcesCompat.getFont(this, R.font.lato_bold)

            binding.btnSortByRating.setTextColor(resources.getColor(R.color.neutral_600))
            binding.btnSortByRating.backgroundTintList = ContextCompat.getColorStateList(this, R.color.black_3)
            binding.btnSortByRating.typeface = ResourcesCompat.getFont(this, R.font.lato_regular)

            adapter.submitList(sortListByDistance(menus!!.toList()))

        }

        binding.backButton.setOnClickListener {
            finish()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    updateMenuDistances(it)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Lokasi tidak dapat diakses", Toast.LENGTH_SHORT).show()
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateMenuDistances(currentLocation: Location) {
        val updatedListMenu = mutableListOf<Menu>()
//        val updatedMenuList = menus.map { menu ->
//            val menuLocation = Location("").apply {
//                latitude = menu.lat
//                longitude = menu.lon
//            }
//            Log.d("Cat", "location $menuLocation")
//            Log.d("Cat", "current $currentLocation")
//            menu.copy(distance = currentLocation.distanceTo(menuLocation).toDouble())
//            val dis = currentLocation.distanceTo(menuLocation)
//            Log.d("Cat", dis.toString())
//        }
        menus.forEach { menu ->
            val menuLocation = Location("").apply {
                latitude = menu.lat
                longitude = menu.lon
            }
            val dis = currentLocation.distanceTo(menuLocation)
            menu.distance = dis.toDouble()
            updatedListMenu.add(menu)
        }
        menuAdapter.submitList(updatedListMenu)
        menuAdapter.notifyDataSetChanged()
    }

    companion object {
        const val EXTRA_MENUS = "extra-menus"
        const val EXTRA_NAME = "extra-name"
        const val EXTRA_USER_LAT = "extra-user-lat"
        const val EXTRA_USER_LON = "extra-user-lon"
    }
}
