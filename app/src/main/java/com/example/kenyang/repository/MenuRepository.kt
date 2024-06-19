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
            price = 15000,
            expireDate = "30 Desember 2024"
        ),
        Menu(
            menuId = "MN002",
            imageId = R.drawable.a2,
            menu = "Nasi Ayam Penyet",
            restaurant = "Warung Pak Kumis",
            restaurantId = 102,
            restaurantAddress = "Jl. Dr. M. Isa No. 10, Palembang",
            lat = -2.974216,
            lon = 104.744869,
            stock = 15,
            rating = 4.2,
            price = 12000,
            expireDate = "25 Desember 2024"
        ),
        Menu(
            menuId = "MN003",
            imageId = R.drawable.a3,
            menu = "Nasi Biryani",
            restaurant = "Resto Green Forest",
            restaurantId = 103,
            restaurantAddress = "Jl. Veteran No. 19, Palembang",
            lat = -2.992994,
            lon = 104.764918,
            stock = 30,
            rating = 4.7,
            price = 20000,
            expireDate = "20 Desember 2024"
        ),
        Menu(
            menuId = "MN004",
            imageId = R.drawable.a4,
            menu = "Sushi",
            restaurant = "FX Street Vendor",
            restaurantId = 104,
            restaurantAddress = "Jl. Jend. Sudirman No. 23, Palembang",
            lat = -2.970115,
            lon = 104.734191,
            stock = 10,
            rating = 3.0,
            price = 25000,
            expireDate = "15 Desember 2024"
        ),
        Menu(
            menuId = "MN005",
            imageId = R.drawable.a5,
            menu = "Chicken Rice Bowl",
            restaurant = "Sukasuki Resto",
            restaurantId = 105,
            restaurantAddress = "Jl. Kol. Atmo No. 20, Palembang",
            lat = -2.980462,
            lon = 104.750594,
            stock = 7,
            rating = 4.6,
            price = 18000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN006",
            imageId = R.drawable.a6,
            menu = "Roti Bakar",
            restaurant = "Paul Bakery",
            restaurantId = 106,
            restaurantAddress = "Jl. Jendral Sudirman No. 20, Palembang",
            lat = -2.980462,
            lon = 104.750594,
            stock = 25,
            rating = 4.4,
            price = 15000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN007",
            imageId = R.drawable.a7,
            menu = "Roti Tawar",
            restaurant = "Ginger Bakery & Sweets",
            restaurantId = 107,
            restaurantAddress = "Jl. Jendral Sudirman No. 21, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 14,
            rating = 4.1,
            price = 17000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN008",
            imageId = R.drawable.a8,
            menu = "Roti Panggang",
            restaurant = "Hannah Bakery & Sweets",
            restaurantId = 110,
            restaurantAddress = "Jl. Jendral Sudirman No. 23, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 15,
            rating = 4.3,
            price = 39000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN009",
            imageId = R.drawable.a9,
            menu = "Cupcake Cokelat",
            restaurant = "Green Bakery",
            restaurantId = 110,
            restaurantAddress = "Jl. Jendral Sudirman No. 21, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 25,
            rating = 4.9,
            price = 28000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN010",
            imageId = R.drawable.a10,
            menu = "Baguette",
            restaurant = "Mustard Bakery",
            restaurantId = 110,
            restaurantAddress = "Jl. Jendral Sudirman No. 27, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 13,
            rating = 4.8,
            price = 18000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN011",
            imageId = R.drawable.a11,
            menu = "Pisang 1 Kg",
            restaurant = "Toko Amanah",
            restaurantId = 110,
            restaurantAddress = "Jl. Jendral Sudirman No. 40, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 11,
            rating = 4.0,
            price = 19000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN012",
            imageId = R.drawable.a12,
            menu = "Apel Manis 500 gr",
            restaurant = "Toko Buah Nutriland",
            restaurantId = 110,
            restaurantAddress = "Jl. Jendral Sudirman No. 90, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 10,
            rating = 4.1,
            price = 27000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN013",
            imageId = R.drawable.a13,
            menu = "Durian 1 Kg",
            restaurant = "Toko Amanah",
            restaurantId = 110,
            restaurantAddress = "Jl. Jendral Sudirman No. 40, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 9,
            rating = 4.4,
            price = 35000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN014",
            imageId = R.drawable.a14,
            menu = "Jeruk 300 gr",
            restaurant = "Toko Sejahtera",
            restaurantId = 110,
            restaurantAddress = "Jl. Jendral Sudirman No. 40, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 8,
            rating = 3.7,
            price = 40000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN015",
            imageId = R.drawable.a15,
            menu = "Rambutan 500 gr",
            restaurant = "Toko Makmur",
            restaurantId = 110,
            restaurantAddress = "Jl. Jendral Sudirman No. 90, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 20,
            rating = 3.8,
            price = 15000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN016",
            imageId = R.drawable.a16,
            menu = "Wortel 250 gr",
            restaurant = "Aneka Buah dan Sayur",
            restaurantId = 110,
            restaurantAddress = "Jl. Jendral Sudirman No. 40, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 11,
            rating = 4.9,
            price = 35000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN017",
            imageId = R.drawable.a17,
            menu = "Selada 250 gr",
            restaurant = "Toko Sayur Grosir Sejehtera",
            restaurantId = 110,
            restaurantAddress = "Jl. Jendral Sudirman No. 40, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 25,
            rating = 4.6,
            price = 35000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN018",
            imageId = R.drawable.a18,
            menu = "Brokoli 500 gr",
            restaurant = "Toko Amanah",
            restaurantId = 110,
            restaurantAddress = "Jl. Jendral Sudirman No. 40, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 14,
            rating = 4.0,
            price = 35000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN019",
            imageId = R.drawable.a19,
            menu = "Bok Choy 300 gr",
            restaurant = "Toko Sayur Segar",
            restaurantId = 110,
            restaurantAddress = "Jl. Jendral Sudirman No. 40, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 18,
            rating = 4.5,
            price = 35000,
            expireDate = "10 Desember 2024"
        ),
        Menu(
            menuId = "MN020",
            imageId = R.drawable.a20,
            menu = "Paprika 1 Kg",
            restaurant = "Toko Ageng",
            restaurantId = 110,
            restaurantAddress = "Jl. Jendral Sudirman No. 40, Palembang",
            lat = -2.980461,
            lon = 104.750594,
            stock = 45,
            rating = 4.7,
            price = 10000,
            expireDate = "10 Desember 2024",
        ),
    )

    private val categories = listOf(
        Category(
            id = 1,
            name = "Nasi",
            image = R.drawable.k1,
            listMenu = menus.subList(0, 5)
        ),
        Category(
            id = 2,
            name = "Roti",
            image = R.drawable.k2,
            listMenu = menus.subList(5, 10)
        ),
        Category(
            id = 3,
            name = "Buah",
            image = R.drawable.k3,
            listMenu = menus.subList(10, 15)
        ),
        Category(
            id = 4,
            name = "Sayur",
            image = R.drawable.k4,
            listMenu = menus.subList(15, 20)
        )


    )

    fun getAllMenus(): List<Menu> {
        return menus
    }

    fun getAllCategories(): List<Category> {
        return categories
    }
}
