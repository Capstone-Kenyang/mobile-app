package com.example.kenyang.ui.activity

import ItemMenuAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kenyang.R
import com.example.kenyang.ui.viewmodel.MenuItem

class CategoryListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemMenuAdapter
    private lateinit var menuItemList: List<MenuItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)

        recyclerView = findViewById(R.id.rv_category_menu_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        menuItemList = listOf(
            MenuItem("Ayam Geprek", "5 Tersedia", "RM Padang", "4.95", "2.3 km", "Rp 15.000"),
            MenuItem("Nasi Goreng", "3 Tersedia", "Warung Nasi", "4.80", "1.8 km", "Rp 12.000")
            // Add more items as needed
        )

        adapter = ItemMenuAdapter(menuItemList)
        recyclerView.adapter = adapter
    }
}
