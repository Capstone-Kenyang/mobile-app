package com.example.kenyang.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenyang.R
import com.example.kenyang.adapter.OrderAdapter
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {

    private val binding: ActivityOrderBinding by lazy {
        ActivityOrderBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adapter = OrderAdapter()
        val list = makeList()
        adapter.submitList(list)

        binding.rvOrder.adapter = adapter
        binding.rvOrder.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun makeList(): List<Order> {
        return listOf(
            Order(
                id = "O001",
                imageId = R.drawable.a1, // Menggunakan drawable a1
                menu = Menu(
                    menuId = "M001",
                    imageId = R.drawable.a1,
                    menu = "Classic Burger",
                    restaurant = "Burger Palace",
                    restaurantId = 101,
                    restaurantAddress = "Jl. Sudirman No.123, Palembang",
                    lat = -2.990934,
                    lon = 104.756554,
                    stock = 50,
                    rating = 4.5,
                    distance = 2.4,
                    price = 7999
                ),
                status = "Diproses" // Status diubah menjadi "Diproses"
            ),
            Order(
                id = "O002",
                imageId = R.drawable.a2, // Menggunakan drawable a2
                menu = Menu(
                    menuId = "M002",
                    imageId = R.drawable.a2,
                    menu = "Pepperoni Pizza",
                    restaurant = "Pizza Heaven",
                    restaurantId = 102,
                    restaurantAddress = "Jl. Rajawali No.456, Palembang",
                    lat = -2.973717,
                    lon = 104.739709,
                    stock = 30,
                    rating = 4.8,
                    distance = 1.1,
                    price = 12999
                ),
                status = "Diproses" // Status diubah menjadi "Diproses"
            ),
            Order(
                id = "O003",
                imageId = R.drawable.a3, // Menggunakan drawable a3
                menu = Menu(
                    menuId = "M003",
                    imageId = R.drawable.a3,
                    menu = "Sushi Platter",
                    restaurant = "Sushi Express",
                    restaurantId = 103,
                    restaurantAddress = "Jl. Angkatan 45 No.789, Palembang",
                    lat = -2.974619,
                    lon = 104.749427,
                    stock = 20,
                    rating = 4.7,
                    distance = 3.7,
                    price = 19999
                ),
                status = "Selesai" // Status diubah menjadi "Selesai"
            ),
            Order(
                id = "O004",
                imageId = R.drawable.a4, // Menggunakan drawable a4
                menu = Menu(
                    menuId = "M004",
                    imageId = R.drawable.a4,
                    menu = "Caesar Salad",
                    restaurant = "Green Bowl",
                    restaurantId = 104,
                    restaurantAddress = "Jl. Jenderal Sudirman No.321, Palembang",
                    lat = -2.976073,
                    lon = 104.761590,
                    stock = 15,
                    rating = 4.2,
                    distance = 1.9,
                    price = 5999
                ),
                status = "Diproses" // Status diubah menjadi "Diproses"
            ),
            Order(
                id = "O005",
                imageId = R.drawable.a5, // Menggunakan drawable a5
                menu = Menu(
                    menuId = "M005",
                    imageId = R.drawable.a5,
                    menu = "Spaghetti Carbonara",
                    restaurant = "Pasta House",
                    restaurantId = 105,
                    restaurantAddress = "Jl. R. Sukamto No.654, Palembang",
                    lat = -2.984700,
                    lon = 104.762859,
                    stock = 25,
                    rating = 4.6,
                    distance = 2.2,
                    price = 14999
                ),
                status = "Selesai" // Status diubah menjadi "Selesai"
            )
        )

    }
}