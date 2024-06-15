package com.example.kenyang.ui.activity

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kenyang.adapter.ItemMenuRecommendationAdapter

class CategoryActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: ItemMenuRecommendationAdapter? = null
    private var menuItemList: MutableList<MenuItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        menuItemList = ArrayList<MenuItem>()
        // Add some dummy data
        menuItemList!!.add(
            MenuItem(
                "Ayam Geprek",
                "5 Tersedia",
                "RM Padang",
                "4.95",
                "2.3 km",
                "Rp 15.000"
            )
        )
        menuItemList!!.add(
            MenuItem(
                "Nasi Goreng",
                "3 Tersedia",
                "Warung Nasi",
                "4.80",
                "1.8 km",
                "Rp 12.000"
            )
        )

        // Add more items as needed
        adapter = ItemMenuRecommendationAdapter(menuItemList!!)
        recyclerView.setAdapter(adapter)
    }
}
