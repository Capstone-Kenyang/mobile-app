package com.example.kenyang.ui.activity

import ItemMenuAdapter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kenyang.R
import com.example.kenyang.adapter.CategoryMenuAdapter
import com.example.kenyang.adapter.MenuAdapter
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.databinding.ActivityCategoryListBinding
import com.example.kenyang.ui.viewmodel.MenuItem

class CategoryListActivity : AppCompatActivity() {

    private val binding: ActivityCategoryListBinding by lazy {
        ActivityCategoryListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val categoryName = intent.getStringExtra(EXTRA_NAME)

        val menus = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableArrayListExtra(EXTRA_MENUS, Menu::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableArrayListExtra(EXTRA_MENUS)
        }

        val adapter = CategoryMenuAdapter()
        adapter.submitList(menus)
        binding.rvCategoryMenuList.adapter = adapter
        binding.rvCategoryMenuList.layoutManager = LinearLayoutManager(this)
        binding.tvCategoryName.text = categoryName
    }

    companion object {
        const val EXTRA_MENUS = "extra-menus"
        const val EXTRA_NAME = "extra-name"
    }
}
