package com.example.kenyang.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kenyang.R
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.databinding.ItemOrderBinding
import com.example.kenyang.ui.activity.OrderDetailActivity

class OrderAdapter : ListAdapter<Order, OrderAdapter.OrderViewHolder>(DIFF_CALLBACK) {

    class OrderViewHolder(private val itemBinding: ItemOrderBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(order: Order) {
            itemBinding.tvOrderId.text = order.id
            itemBinding.ivOrderImage.setImageResource(order.imageId)
            itemBinding.tvOrderMenu.text = order.menu.menu
            itemBinding.tvOrderRestaurant.text = order.menu.restaurant
            itemBinding.tvOrderStatus.text = order.status

            if (order.status == "Selesai") {
                itemBinding.tvOrderStatus.setBackgroundResource(R.drawable.accent_rounded_blue)
                itemBinding.tvOrderStatus.setTextColor(Color.parseColor("#5508AA"))
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, OrderDetailActivity::class.java)
                intent.putExtra(OrderDetailActivity.EXTRA_ORDER, order)
                itemView.context.startActivity(intent)
            }
        }

        // set on click listener to category page with switch

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }


    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }

        }
    }
}