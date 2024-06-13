package com.example.kenyang.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenyang.R
import com.example.kenyang.adapter.OrderAdapter
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
        val orders = listOf(
            Order(
                id = "XX110-VW103",
                imageId = R.drawable.a1,
                menu = "Cheese Burger",
                restaurant = "Burger King",
                status = "Diproses"
            ),
            Order(
                id = "XX122-VW093",
                imageId = R.drawable.a2,
                menu = "Salmon Sushi",
                restaurant = "Sushi Tei",
                status = "Diproses"
            ),
            Order(
                id = "XX110-VW103",
                imageId = R.drawable.a3,
                menu = "Pepperoni Pizza",
                restaurant = "Pizza Hut",
                status = "Selesai"
            ),
            Order(
                id = "XX110-VW103",
                imageId = R.drawable.a4,
                menu = "Spaghetti Carbonara",
                restaurant = "Olive Garden",
                status = "Selesai"
            ),
            Order(
                id = "XX110-VW103",
                imageId = R.drawable.a5,
                menu = "Caesar Salad",
                restaurant = "Green Bowl",
                status = "Selesai"
            )
        )

        return orders

    }
}