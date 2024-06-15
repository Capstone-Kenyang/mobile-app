package com.example.kenyang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kenyang.R
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.databinding.ItemMenuRecommendationBinding
import com.example.kenyang.ui.fragments.MenuDetailFragment
import java.text.NumberFormat
import java.util.Locale

class RecommendationAdapter : ListAdapter<Menu, RecommendationAdapter.RecommendationViewHolder>(DIFF_CALLBACK) {
    class RecommendationViewHolder (private val itemBinding: ItemMenuRecommendationBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(menu: Menu) {
            val locale = Locale("id", "ID")
            val formattedPrice = NumberFormat.getNumberInstance(locale).format(menu.price)

            itemBinding.ivRecommendationImage.setImageResource(menu.imageId)
            itemBinding.tvMenu.text = menu.menu
            itemBinding.tvRestaurant.text = menu.restaurant
            itemBinding.tvStock.text = itemView.context.resources.getString(R.string.stock, menu.stock)
            itemBinding.tvRating.text = menu.rating.toString()
            itemBinding.tvDistance.text = menu.distance.toString()
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
    ): RecommendationAdapter.RecommendationViewHolder {
        val binding = ItemMenuRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationAdapter.RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationAdapter.RecommendationViewHolder, position: Int) {
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