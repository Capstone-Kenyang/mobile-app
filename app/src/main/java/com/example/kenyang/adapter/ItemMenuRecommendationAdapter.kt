package com.example.kenyang.adapter

import android.R
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemMenuRecommendationAdapter(private val menuItems: List<MenuItem>) :
    RecyclerView.Adapter<ItemMenuRecommendationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu_recommendation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuItem = menuItems[position]
        // Bind data to ViewHolder
        holder.tvMenu.setText(menuItem.getMenuName())
        holder.tvStock.setText(menuItem.getStock())
        holder.tvRestaurant.setText(menuItem.getRestaurantName())
        holder.tvRating.setText(menuItem.getRating())
        holder.tvDistance.setText(menuItem.getDistance())
        holder.tvPrice.setText(menuItem.getPrice())
        // Optionally set image resource here if needed
        // holder.ivRecommendationImage.setImageResource(menuItem.getImageResId());
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivRecommendationImage: ImageView =
            itemView.findViewById<ImageView>(R.id.iv_recommendation_image)
        var ivStar: ImageView = itemView.findViewById<ImageView>(R.id.iv_star)
        var tvMenu: TextView = itemView.findViewById<TextView>(R.id.tv_menu)
        var tvStock: TextView = itemView.findViewById<TextView>(R.id.tv_stock)
        var tvRestaurant: TextView = itemView.findViewById<TextView>(R.id.tv_restaurant)
        var tvRating: TextView = itemView.findViewById<TextView>(R.id.tv_rating)
        var tvDistance: TextView = itemView.findViewById<TextView>(R.id.tv_distance)
        var tvPrice: TextView = itemView.findViewById<TextView>(R.id.tv_price)
    }
}
