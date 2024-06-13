package com.example.kenyang.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.databinding.ItemMenuRecommendationBinding

class RecommendationAdapter : ListAdapter<Menu, RecommendationAdapter.RecommendationViewHolder>(DIFF_CALLBACK) {
    class RecommendationViewHolder (private val itemBinding: ItemMenuRecommendationBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(recommendation: Menu) {
            itemBinding.ivRecommendationImage.setImageResource(recommendation.imageId)
            itemBinding.tvMenu.text = recommendation.menu
            itemBinding.tvRestaurant.text = recommendation.restaurant
            itemBinding.tvStock.text = recommendation.stock.toString()
            itemBinding.tvRating.text = recommendation.rating.toString()
            itemBinding.tvDistance.text = recommendation.distance.toString()
            itemBinding.tvPrice.text = recommendation.price.toString()
        }

        // set on click listener to category page with switch
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