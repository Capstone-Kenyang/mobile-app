package com.example.kenyang.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kenyang.R
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.databinding.ActivityOrderDetailBinding
import com.example.kenyang.factory.ViewModelFactory
import com.example.kenyang.ui.fragments.MapsFragment
import com.example.kenyang.ui.fragments.OrderStatusFragment
import com.example.kenyang.ui.viewmodel.OrderDetailViewModel
import java.text.NumberFormat
import java.util.Locale

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding

    private val orderDetailViewModel: OrderDetailViewModel by viewModels<OrderDetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val order = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_ORDER, Order::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_ORDER)
        }

        val locale = Locale("id", "ID")
        val price = NumberFormat.getNumberInstance(locale).format(order?.menu?.price)

        binding.tvOrderId.text = order!!.id
        binding.tvMenu.text = order.menu.menu
        binding.tvRestaurant.text = order.menu.restaurant
        binding.tvAddress.text = order.menu.restaurantAddress
        binding.tvPrice.text = resources.getString(R.string.price, price)

        val lat = order.menu.lat
        val lon = order.menu.lon

        setButtonVisibility(order.isComplete, order.isDonation)

        val statusInfo = if (order.isComplete && !order.isDonation) {
            resources.getString(R.string.status_info_order_done)
        } else if (order.isDonation) {
            resources.getString(R.string.status_donation_info_not_done)
        } else {
            ""
        }

        val orderStatusFragment = OrderStatusFragment.newInstance(statusInfo)
        inflateFragment(orderStatusFragment, R.id.status_order_info_container, order.isComplete, order.isDonation)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val mapFragment = MapsFragment.newInstance(lat, lon)

        fragmentTransaction.replace(R.id.fragment_maps, mapFragment)
        fragmentTransaction.commit()

        // pindah ke map kalau diklik
        binding.tvClickableCheckLocation.setOnClickListener {
            val label = order.menu.restaurant
            val uri = "geo:$lat,$lon?q=$lat,$lon($label)"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))

            intent.setPackage("com.google.android.apps.maps")

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/maps?q=$lat,$lon"))
                startActivity(webIntent)
            }
        }


        binding.buttonSend.setOnClickListener {
            order.isComplete = true
            orderDetailViewModel.updateOrder(order)
            makeToast("Pesanan telah diterima")
            this.recreate()
        }

    }

    private fun setButtonVisibility(isComplete: Boolean, isDonation: Boolean) {
        if (isComplete || isDonation) {
            binding.buttonSend.visibility = View.GONE
            binding.statusOrderInfoContainer.visibility = View.VISIBLE
        }
    }

    private fun inflateFragment(fragment: Fragment, containerId: Int, isComplete: Boolean, isDonation: Boolean) {
        if (isComplete || isDonation) {
            supportFragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commit()
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    companion object {
        const val EXTRA_ORDER = "extra-order"
    }

}