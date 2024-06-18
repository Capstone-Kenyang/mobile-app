package com.example.kenyang.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kenyang.R
import com.example.kenyang.data.dataclass.Menu

class MainViewModel : ViewModel() {
    private val _allMenus = MutableLiveData<List<Menu>>()
    val allMenus: LiveData<List<Menu>> get() = _allMenus

    init {
        _allMenus.value = loadInitialMenus()
    }

    // Function to update the list of menus
    fun updateMenuList(updatedMenus: List<Menu>) {
        _allMenus.value = updatedMenus
    }

    // Function to load initial menus (You can replace this with actual data loading)
    private fun loadInitialMenus(): List<Menu> {
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
                distance = 2.0,
                price = 15000,
                expireDate = "30-12-2024"
            ),
            Menu(
                menuId = "MN002",
                imageId = R.drawable.a2,
                menu = "Mie Ayam",
                restaurant = "Mie Ayam Pak Gino",
                restaurantId = 102,
                restaurantAddress = "Jl. Soekarno-Hatta No. 21, Palembang",
                lat = -2.970934,
                lon = 104.732344,
                stock = 15,
                rating = 4.8,
                distance = 1.5,
                price = 12000,
                expireDate = "30-12-2024"
            ),
            Menu(
                menuId = "MN003",
                imageId = R.drawable.a3,
                menu = "Sate Ayam",
                restaurant = "Sate Madura Bang Jali",
                restaurantId = 103,
                restaurantAddress = "Jl. Ahmad Yani No. 12, Palembang",
                lat = -2.995434,
                lon = 104.755544,
                stock = 10,
                rating = 4.7,
                distance = 3.5,
                price = 20000,
                expireDate = "30-12-2024"
            ),
            Menu(
                menuId = "MN004",
                imageId = R.drawable.a4,
                menu = "Bakso",
                restaurant = "Bakso Pak Kumis",
                restaurantId = 104,
                restaurantAddress = "Jl. Sudirman No. 34, Palembang",
                lat = -2.985434,
                lon = 104.745344,
                stock = 25,
                rating = 4.3,
                distance = 2.2,
                price = 15000,
                expireDate = "30-12-2024"
            ),
            Menu(
                menuId = "MN005",
                imageId = R.drawable.a5,
                menu = "Gado-Gado",
                restaurant = "Gado-Gado Bu Ani",
                restaurantId = 105,
                restaurantAddress = "Jl. Demang Lebar Daun No. 45, Palembang",
                lat = -2.975934,
                lon = 104.725344,
                stock = 30,
                rating = 4.6,
                distance = 1.8,
                price = 13000,
                expireDate = "30-12-2024"
            )
        )
    }
}