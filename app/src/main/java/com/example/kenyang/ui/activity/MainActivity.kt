package com.example.kenyang.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenyang.R
import com.example.kenyang.adapter.RecommendationAdapter
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        val firebaseUser = auth.currentUser


        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        val firstName = firebaseUser.displayName?.split(" ")?.get(0) ?: "User"
        binding.tvUserFirstName.text = resources.getString(R.string.greeting_message, firstName)

        binding.btnSignOut.setOnClickListener {
            signOut()
        }

        val adapter = RecommendationAdapter()
        val recommendationList = makeList()
        adapter.submitList(recommendationList)

        binding.rvRecommendation.adapter = adapter
        binding.rvRecommendation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun makeList(): List<Menu> {

        return listOf(
            Menu(
                menuId = "M001",
                imageId = R.drawable.a1, // Asumsikan ada drawable dengan nama ini
                menu = "Classic Burger",
                restaurant = "Burger Palace",
                restaurantId = 101,
                restaurantAddress = "Jl. Sudirman No.123, Palembang",
                lat = -2.990934, // Latitude restoran
                lon = 104.756554, // Longitude restoran
                stock = 50,
                rating = 4.5,
                distance = 2.4, // dalam kilometer
                price = 7999 // harga dalam rupiah
            ),
            Menu(
                menuId = "M002",
                imageId = R.drawable.a2, // Asumsikan ada drawable dengan nama ini
                menu = "Pepperoni Pizza",
                restaurant = "Pizza Heaven",
                restaurantId = 102,
                restaurantAddress = "Jl. Rajawali No.456, Palembang",
                lat = -2.973717, // Latitude restoran
                lon = 104.739709, // Longitude restoran
                stock = 30,
                rating = 4.8,
                distance = 1.1, // dalam kilometer
                price = 12999 // harga dalam rupiah
            ),
            Menu(
                menuId = "M003",
                imageId = R.drawable.a1, // Asumsikan ada drawable dengan nama ini
                menu = "Sushi Platter",
                restaurant = "Sushi Express",
                restaurantId = 103,
                restaurantAddress = "Jl. Angkatan 45 No.789, Palembang",
                lat = -2.974619, // Latitude restoran
                lon = 104.749427, // Longitude restoran
                stock = 20,
                rating = 4.7,
                distance = 3.7, // dalam kilometer
                price = 19999 // harga dalam rupiah
            ),
            Menu(
                menuId = "M004",
                imageId = R.drawable.a2, // Asumsikan ada drawable dengan nama ini
                menu = "Caesar Salad",
                restaurant = "Green Bowl",
                restaurantId = 104,
                restaurantAddress = "Jl. Jenderal Sudirman No.321, Palembang",
                lat = -2.976073, // Latitude restoran
                lon = 104.761590, // Longitude restoran
                stock = 15,
                rating = 4.2,
                distance = 1.9, // dalam kilometer
                price = 5999 // harga dalam rupiah
            ),
            Menu(
                menuId = "M005",
                imageId = R.drawable.a3, // Asumsikan ada drawable dengan nama ini
                menu = "Spaghetti Carbonara",
                restaurant = "Pasta House",
                restaurantId = 105,
                restaurantAddress = "Jl. R. Sukamto No.654, Palembang",
                lat = -2.984700, // Latitude restoran
                lon = 104.762859, // Longitude restoran
                stock = 25,
                rating = 4.6,
                distance = 2.2, // dalam kilometer
                price = 14999 // harga dalam rupiah
            )
        )

    }

    private fun signOut() {
        lifecycleScope.launch {
            val credentialManager = CredentialManager.create(this@MainActivity)

            auth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }

//    private fun makeList(): List<Menu> {
//        val list = mutableListOf<Menu>()
//
//        val images = resources.obtainTypedArray(R.array.menu_image)
//        val menu = resources.getStringArray(R.array.menu_array)
//        val restaurant = resources.getStringArray(R.array.restaurant_array)
//        val stock = resources.getIntArray(R.array.stock_array)
//        val rating = resources.getStringArray(R.array.rating_array)
//        val distance = resources.getStringArray(R.array.distance_array)
//        val price = resources.getIntArray(R.array.price_array)
//
//        for (i in menu.indices) {
//            val recommendation = Menu(
//                images.getResourceId(i, -1),
//                menu[i],
//                restaurant[i],
//                stock[i],
//                rating[i].toDouble(),
//                distance[i].toDouble(),
//                price[i]
//            )
//            list.add(recommendation)
//        }
//
//        return list
//    }
}