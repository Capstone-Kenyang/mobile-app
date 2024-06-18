package com.example.kenyang.data

import com.example.kenyang.R
import com.example.kenyang.data.dataclass.Category
import com.example.kenyang.data.dataclass.Menu

class MenuRepository {

    private val menus = listOf(
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
            expireDate = "30 Desember 2024"
        ),
        Menu(
            menuId = "MN002",
            imageId = R.drawable.a2,
            menu = "Nasi Bakar",
            restaurant = "Warung Pak Kumis",
            restaurantId = 102,
            restaurantAddress = "Jl. Dr. M. Isa No. 10, Palembang",
            lat = -2.974216,
            lon = 104.744869,
            stock = 15,
            rating = 4.7,
            distance = 1.5,
            price = 12000,
            expireDate = "25 Desember 2024"
        ),
        Menu(
            menuId = "MN003",
            imageId = R.drawable.a3,
            menu = "Nasi Minyak",
            restaurant = "Sate Khas Padang",
            restaurantId = 103,
            restaurantAddress = "Jl. Veteran No. 19, Palembang",
            lat = -2.992994,
            lon = 104.764918,
            stock = 30,
            rating = 4.8,
            distance = 3.0,
            price = 20000,
            expireDate = "20 Desember 2024"
        ),
        Menu(
            menuId = "MN004",
            imageId = R.drawable.a4,
            menu = "Nasi Rendang",
            restaurant = "Restoran Padang",
            restaurantId = 104,
            restaurantAddress = "Jl. Jend. Sudirman No. 23, Palembang",
            lat = -2.970115,
            lon = 104.734191,
            stock = 10,
            rating = 4.6,
            distance = 2.5,
            price = 25000,
            expireDate = "15 Desember 2024"
        ),
        Menu(
            menuId = "MN005",
            imageId = R.drawable.a5,
            menu = "Nasi Ayam Geprek",
            restaurant = "Ayam Geprek",
            restaurantId = 105,
            restaurantAddress = "Jl. Kol. Atmo No. 20, Palembang",
            lat = -2.980462,
            lon = 104.750594,
            stock = 25,
            rating = 4.4,
            distance = 1.0,
            price = 18000,
            expireDate = "10 Desember 2024"
        )
    )

    private val categories = listOf(
        Category(
            id = 1,
            name = "Nasi",
            image = R.drawable.bakery,
            listMenu = menus.subList(0, 2)
        ),
        Category(
            id = 2,
            name = "Roti",
            image = R.drawable.bakery,
            listMenu = menus.subList(2, 3)
        ),
        Category(
            id = 3,
            name = "Buah",
            image = R.drawable.bakery,
            listMenu = menus.subList(3, 5)
        ),
        Category(
            id = 3,
            name = "Sayur",
            image = R.drawable.bakery,
            listMenu = menus.subList(3, 5)
        )


    )

    fun getAllMenus(): List<Menu> {
        return menus
    }

    fun getAllCategories(): List<Category> {
        return categories
    }
}
