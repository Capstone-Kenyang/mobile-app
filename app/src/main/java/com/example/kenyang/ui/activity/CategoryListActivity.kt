package com.example.kenyang.ui.activity

import ItemMenuAdapter
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kenyang.R
import com.example.kenyang.adapter.CategoryMenuAdapter
import com.example.kenyang.adapter.MenuAdapter
import com.example.kenyang.converter.sortListByDistance
import com.example.kenyang.converter.sortListByRating
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.databinding.ActivityCategoryListBinding
import com.example.kenyang.ui.viewmodel.CategoryListViewModel
import com.example.kenyang.ui.viewmodel.MenuItem

class CategoryListActivity : AppCompatActivity() {

    private val binding: ActivityCategoryListBinding by lazy {
        ActivityCategoryListBinding.inflate(layoutInflater)
    }

    private val sortKey = MutableLiveData<String>()
    private val categoryListViewModel: CategoryListViewModel by viewModels<CategoryListViewModel>()

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
        binding.rvCategoryMenuList.adapter = adapter
        binding.rvCategoryMenuList.layoutManager = LinearLayoutManager(this)
        binding.tvCategoryName.text = categoryName
        adapter.submitList(menus)


        binding.btnSortByRating.setOnClickListener {
            binding.btnSortByRating.setTextColor(resources.getColor(R.color.blue))
            binding.btnSortByRating.backgroundTintList = ContextCompat.getColorStateList(this, R.color.indigo_50)
            binding.btnSortByRating.typeface = ResourcesCompat.getFont(this, R.font.lato_bold)

            binding.btnSortByDistance.setTextColor(resources.getColor(R.color.neutral_600))
            binding.btnSortByDistance.backgroundTintList = ContextCompat.getColorStateList(this, R.color.black_3)
            binding.btnSortByDistance.typeface = ResourcesCompat.getFont(this, R.font.lato_regular)

            adapter.submitList(sortListByRating(menus!!.toList()))
        }

        binding.btnSortByDistance.setOnClickListener {
            binding.btnSortByDistance.setTextColor(resources.getColor(R.color.blue))
            binding.btnSortByDistance.backgroundTintList = ContextCompat.getColorStateList(this, R.color.indigo_50)
            binding.btnSortByDistance.typeface = ResourcesCompat.getFont(this, R.font.lato_bold)

            binding.btnSortByRating.setTextColor(resources.getColor(R.color.neutral_600))
            binding.btnSortByRating.backgroundTintList = ContextCompat.getColorStateList(this, R.color.black_3)
            binding.btnSortByRating.typeface = ResourcesCompat.getFont(this, R.font.lato_regular)

            adapter.submitList(sortListByDistance(menus!!.toList()))

        }

    }

    companion object {
        const val EXTRA_MENUS = "extra-menus"
        const val EXTRA_NAME = "extra-name"
    }
}
