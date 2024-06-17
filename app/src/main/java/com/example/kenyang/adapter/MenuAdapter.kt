package com.example.kenyang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kenyang.R
import com.example.kenyang.converter.toSingleDecimal
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.databinding.ItemMenuRecommendationBinding
import com.example.kenyang.ui.fragments.MenuDetailFragment
import java.text.NumberFormat
import java.util.Locale

class MenuAdapter : ListAdapter<Menu, MenuAdapter.RecommendationViewHolder>(DIFF_CALLBACK) {
    class RecommendationViewHolder (private val itemBinding: ItemMenuRecommendationBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(menu: Menu) {
            val locale = Locale("id", "ID")
            val formattedPrice = NumberFormat.getNumberInstance(locale).format(menu.price)
            val distance = menu.distance / 1000

            itemBinding.ivRecommendationImage.setImageResource(menu.imageId)
            itemBinding.tvMenu.text = menu.menu
            itemBinding.tvRestaurant.text = menu.restaurant
            itemBinding.tvStock.text = itemView.context.resources.getString(R.string.stock, menu.stock)
            itemBinding.tvRating.text = menu.rating.toString()
            itemBinding.tvDistance.text = itemView.context.getString(R.string.distance, distance.toSingleDecimal())
            itemBinding.tvPrice.text = itemView.context.resources.getString(R.string.price, formattedPrice)

            itemView.setOnClickListener {
//                onOrderClick(recommendation)

                val bottomSheet = MenuDetailFragment.newInstance(menu)
                bottomSheet.show((itemView.context as AppCompatActivity).supportFragmentManager, MenuDetailFragment.TAG)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuAdapter.RecommendationViewHolder {
        val binding = ItemMenuRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuAdapter.RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuAdapter.RecommendationViewHolder, position: Int) {
        val recommendation = getItem(position)
        holder.bind(recommendation)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Menu>() {
            override fun areItemsTheSame(
                oldItem: Menu,
                newItem: Menu
            ): Boolean {
                return oldItem == newItem

            }

            override fun areContentsTheSame(
                oldItem: Menu,
                newItem: Menu
            ): Boolean {
                return oldItem == newItem

            }


        }
    }

}