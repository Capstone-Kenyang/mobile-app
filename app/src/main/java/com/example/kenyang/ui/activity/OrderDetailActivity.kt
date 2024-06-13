package com.example.kenyang.ui.activity

import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.kenyang.R
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.databinding.ActivityOrderDetailBinding

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val order = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_ORDER, Order::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_ORDER)
        }

        binding.tvOrderId.text = order!!.id
        binding.tvMenu.text = order.menu.menu
        binding.tvRestaurant.text = order.menu.restaurant
        binding.tvAddress.text = order.menu.restaurantAddress
        binding.tvPrice.text = order.menu.price.toString()

    }

    companion object {
        const val EXTRA_ORDER = "extra-order"
    }

}