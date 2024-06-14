package com.example.kenyang.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils.replace
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.kenyang.R
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.databinding.ActivityOrderDetailBinding
import com.example.kenyang.ui.fragments.MapsFragment
import java.text.NumberFormat
import java.util.Locale

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding

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

        // untuk menampilkan map dalam container
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val mapFragment = MapsFragment.newInstance(lat, lon)

        fragmentTransaction.replace(R.id.fragment_maps, mapFragment)
        fragmentTransaction.commit()

        // pindah ke map kalau diklik
        binding.tvClickableCheckLocation.setOnClickListener {
            val label = order.menu.restaurant
            val uri = "geo:$lat,$lon?q=$lat,$lon($label)"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))

            // Mengecek apakah aplikasi Google Maps terinstal
            intent.setPackage("com.google.android.apps.maps")

            // Jika Google Maps tidak terinstal, buka web browser
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/maps?q=$lat,$lon"))
                startActivity(webIntent)
            }
        }
    }

    companion object {
        const val EXTRA_ORDER = "extra-order"
    }

}