package com.example.kenyang.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kenyang.data.dataclass.Category
import com.example.kenyang.databinding.ItemRoundCategoryBinding
import com.example.kenyang.ui.activity.CategoryListActivity
import java.util.ArrayList

class CategoriesAdapter : ListAdapter<Category, CategoriesAdapter.CategoryViewHolder>(DIFF_CALLBACK) {

    class CategoryViewHolder(private val itemBinding: ItemRoundCategoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(category: Category) {
            itemBinding.tvCategoryName.text = category.name
            itemBinding.ivImage.setImageResource(category.image)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, CategoryListActivity::class.java)
                intent.putExtra(CategoryListActivity.EXTRA_NAME, category.name)
                intent.putExtra(CategoryListActivity.EXTRA_MENUS, ArrayList(category.listMenu))
                itemView.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesAdapter.CategoryViewHolder {
        val binding = ItemRoundCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CategoriesAdapter.CategoryViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }

        }
    }
}