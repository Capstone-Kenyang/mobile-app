package com.example.kenyang.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenyang.R
import com.example.kenyang.adapter.OrderAdapter
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.databinding.ActivityOrderBinding
import com.example.kenyang.factory.ViewModelFactory
import com.example.kenyang.ui.viewmodel.OrderListViewModel
import kotlinx.coroutines.launch

class OrderListActivity : AppCompatActivity() {

    private val binding: ActivityOrderBinding by lazy {
        ActivityOrderBinding.inflate(layoutInflater)
    }

    private val orderListViewModel: OrderListViewModel by viewModels<OrderListViewModel> {
        ViewModelFactory.getInstance(application)
    }
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

        orderListViewModel.getAllOrder().observe(this@OrderListActivity) {
            inflateRvLayout(it)
        }

    }

    private fun inflateRvLayout(list: List<Order>) {
        val adapter = OrderAdapter()
        adapter.submitList(list)
        binding.rvOrder.adapter = adapter
        binding.rvOrder.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun makeList(): List<Order> {
//        return listOf(
//            Order(
//                id = "MX123-AWU80",
//                menu = Menu(
//                    menuId = "1",
//                    imageId = R.drawable.a1,
//                    menu = "Pempek",
//                    restaurant = "Pempek Beringin",
//                    restaurantId = 101,
//                    restaurantAddress = "Jl. Dr. M. Isa No.40, Palembang",
//                    lat = -2.980122,
//                    lon = 104.737580,
//                    stock = 50,
//                    rating = 4.5,
//                    distance = 1.2,
//                    price = 15000,
//                    expireDate = "27 Mei 2025"
//                ),
//                isComplete = false,
//                isDonation = false
//            ),
//            Order(
//                id = "AWU80-MX123",
//                imageId = R.drawable.a2,
//                menu = Menu(
//                    menuId = "2",
//                    imageId = R.drawable.a2,
//                    menu = "Mie Celor",
//                    restaurant = "Mie Celor 26 Ilir H.M. Syafei Z",
//                    restaurantId = 102,
//                    restaurantAddress = "Jl. Merdeka No.54, Palembang",
//                    lat = -2.978899,
//                    lon = 104.746980,
//                    stock = 30,
//                    rating = 4.7,
//                    distance = 1.5,
//                    price = 20000,
//                    expireDate = "27 Mei 2025"
//                ),
//                status = "Diproses",
//                isDonation = false
//            ),
//            Order(
//                id = "QWERTY-12345",
//                imageId = R.drawable.a3,
//                menu = Menu(
//                    menuId = "3",
//                    imageId = R.drawable.a3,
//                    menu = "Nasi Campur",
//                    restaurant = "Warung Aba",
//                    restaurantId = 103,
//                    restaurantAddress = "Jl. Sultan Mahmud Badaruddin II, Palembang",
//                    lat = -2.981174,
//                    lon = 104.744632,
//                    stock = 20,
//                    rating = 4.2,
//                    distance = 0.8,
//                    price = 18000,
//                    expireDate = "27 Mei 2025"
//                ),
//                status = "Diproses",
//                isDonation = false
//            ),
//            Order(
//                id = "ABCDE-98765",
//                imageId = R.drawable.a4,
//                menu = Menu(
//                    menuId = "4",
//                    imageId = R.drawable.a4,
//                    menu = "Pindang Patin",
//                    restaurant = "RM Pindang Musi Rawas",
//                    restaurantId = 104,
//                    restaurantAddress = "Jl. Veteran, Palembang",
//                    lat = -2.972663,
//                    lon = 104.738133,
//                    stock = 25,
//                    rating = 4.6,
//                    distance = 2.0,
//                    price = 25000,
//                    expireDate = "27 Mei 2025"
//                ),
//                status = "Diproses",
//                isDonation = false
//            ),
//            Order(
//                id = "ZXC987-POI321",
//                imageId = R.drawable.a5,
//                menu = Menu(
//                    menuId = "5",
//                    imageId = R.drawable.a5,
//                    menu = "Kopi Gayo",
//                    restaurant = "Kopi Darat Cafe",
//                    restaurantId = 105,
//                    restaurantAddress = "Jl. Bangau No.5, Palembang",
//                    lat = -2.984313,
//                    lon = 104.749750,
//                    stock = 15,
//                    rating = 4.8,
//                    distance = 1.0,
//                    price = 12000,
//                    expireDate = "27 Mei 2025"
//                ),
//                status = "Diproses",
//                isDonation = false
//            )
//        )


//    }
}