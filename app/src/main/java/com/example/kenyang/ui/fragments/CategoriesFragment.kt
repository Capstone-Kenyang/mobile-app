package com.example.kenyang.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenyang.R
import com.example.kenyang.adapter.CategoriesAdapter
import com.example.kenyang.adapter.MenuAdapter
import com.example.kenyang.data.dataclass.Category
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.databinding.FragmentCategoriesBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private var category = makeCategoryMenus()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = CategoriesAdapter()
        adapter.submitList(category)

        binding.rvCategory.adapter = adapter
        binding.rvCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun makeCategoryMenus(): List<Category> {
        val menu1 = Menu(
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
        )

        val menu2 = Menu(
            menuId = "MN002",
            imageId = R.drawable.a2,
            menu = "Mie Ayam",
            restaurant = "Bakmi Pak Sugeng",
            restaurantId = 102,
            restaurantAddress = "Jl. Jenderal Sudirman No. 25, Palembang",
            lat = -2.977466,
            lon = 104.742776,
            stock = 15,
            rating = 4.2,
            price = 18000,
            expireDate = "25 Desember 2024"
        )

        val menu3 = Menu(
            menuId = "MN003",
            imageId = R.drawable.a3,
            menu = "Sate Ayam",
            restaurant = "Sate Pak Kumis",
            restaurantId = 103,
            restaurantAddress = "Jl. Letnan Simanjuntak No. 2, Palembang",
            lat = -2.983445,
            lon = 104.748356,
            stock = 10,
            rating = 4.7,
            price = 20000,
            expireDate = "20 Desember 2024"
        )

        val menu4 = Menu(
            menuId = "MN004",
            imageId = R.drawable.a4,
            menu = "Rendang",
            restaurant = "Rumah Makan Padang",
            restaurantId = 104,
            restaurantAddress = "Jl. Pangeran Antasari No. 30, Palembang",
            lat = -2.994365,
            lon = 104.734656,
            stock = 8,
            rating = 4.9,
            price = 25000,
            expireDate = "28 Desember 2024"
        )

        val menu5 = Menu(
            menuId = "MN005",
            imageId = R.drawable.a5,
            menu = "Bakso",
            restaurant = "Bakso Mas Tarman",
            restaurantId = 105,
            restaurantAddress = "Jl. Cipto Mangunkusumo No. 9, Palembang",
            lat = -2.978945,
            lon = 104.737856,
            stock = 30,
            rating = 4.8,
            price = 15000,
            expireDate = "15 Desember 2024"
        )

        val menu6 = Menu(
            menuId = "MN006",
            imageId = R.drawable.a2,
            menu = "Ayam Bakar",
            restaurant = "Warung Bu Tini",
            restaurantId = 106,
            restaurantAddress = "Jl. Merdeka No. 45, Palembang",
            lat = -2.975675,
            lon = 104.733445,
            stock = 12,
            rating = 4.3,
            price = 22000,
            expireDate = "22 Desember 2024"
        )

        val menu7 = Menu(
            menuId = "MN007",
            imageId = R.drawable.a3,
            menu = "Pempek",
            restaurant = "Pempek Pak Raden",
            restaurantId = 107,
            restaurantAddress = "Jl. Kolonel Atmo No. 50, Palembang",
            lat = -2.976435,
            lon = 104.747634,
            stock = 50,
            rating = 4.9,
            price = 10000,
            expireDate = "18 Desember 2024"
        )

        val menu8 = Menu(
            menuId = "MN008",
            imageId = R.drawable.a4,
            menu = "Es Teler",
            restaurant = "Es Teler Bu Ani",
            restaurantId = 108,
            restaurantAddress = "Jl. Rajawali No. 30, Palembang",
            lat = -2.980375,
            lon = 104.739524,
            stock = 20,
            rating = 4.6,
            price = 12000,
            expireDate = "26 Desember 2024"
        )

        val menu9 = Menu(
            menuId = "MN009",
            imageId = R.drawable.a5,
            menu = "Gado-Gado",
            restaurant = "Gado-Gado Bu Jum",
            restaurantId = 109,
            restaurantAddress = "Jl. Panca Usaha No. 9, Palembang",
            lat = -2.979564,
            lon = 104.741234,
            stock = 25,
            rating = 4.7,
            price = 14000,
            expireDate = "27 Desember 2024"
        )

        val menu10 = Menu(
            menuId = "MN010",
            imageId = R.drawable.a1,
            menu = "Es Campur",
            restaurant = "Es Campur Pak De",
            restaurantId = 110,
            restaurantAddress = "Jl. Kapten A. Rivai No. 2, Palembang",
            lat = -2.975645,
            lon = 104.748964,
            stock = 15,
            rating = 4.5,
            price = 13000,
            expireDate = "29 Desember 2024"
        )

        // Categories with their respective menus
        val categoryList = listOf(
            Category(
                id = 1,
                name = "Makanan Utama",
                image = R.drawable.a1,
                listMenu = listOf(menu1, menu2, menu3, menu4, menu5)
            ),
            Category(
                id = 2,
                name = "Makanan Ringan",
                image = R.drawable.a2,
                listMenu = listOf(menu6, menu7, menu8, menu9, menu10)
            ),
            Category(
                id = 3,
                name = "Minuman",
                image = R.drawable.a3,
                listMenu = listOf(menu1, menu5, menu7, menu9, menu10)
            ),
            Category(
                id = 4,
                name = "Makanan Tradisional",
                image = R.drawable.a4,
                listMenu = listOf(menu3, menu4, menu6, menu7, menu8)
            ),
            Category(
                id = 5,
                name = "Dessert",
                image = R.drawable.a5,
                listMenu = listOf(menu2, menu4, menu6, menu8, menu10)
            )
        )

        return categoryList
    }



}