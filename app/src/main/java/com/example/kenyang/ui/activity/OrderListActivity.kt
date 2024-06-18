package com.example.kenyang.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
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
import com.example.kenyang.ui.adapter.OrderAdapter
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
            if (it.isEmpty()) {
                binding.tvNoOrderYet.visibility = View.VISIBLE
            } else {
                inflateRvLayout(it.reversed())
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun inflateRvLayout(list: List<Order>) {
        val adapter = OrderAdapter()
        adapter.submitList(list)
        binding.rvOrder.adapter = adapter
        binding.rvOrder.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

}