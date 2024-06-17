package com.example.kenyang.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenyang.R
import com.example.kenyang.adapter.MenuAdapter
import com.example.kenyang.converter.sortListByDistance
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.data.local.OrderDatabase
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val orderDatabase = OrderDatabase.getDatabase(this)
        val orderDao = orderDatabase.orderDao()

//        lifecycleScope.launch {
//            try {
//                orderDao.deleteAllOrders()
//                Log.d("MainActivity", "Semua data berhasil dihapus.")
//            } catch (e: Exception) {
//                Log.e("MainActivity", "Gagal menghapus data: ${e.message}")
//            }
//        }

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

        val adapter = MenuAdapter()
        val list = makeList()
        val sortedByDistanceMenu = sortListByDistance(list)
        adapter.submitList(sortedByDistanceMenu)

        binding.rvRecommendation.adapter = adapter
        binding.rvRecommendation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val secondAdapter = MenuAdapter()
        secondAdapter.submitList(list)
        binding.rvSecondRecommendation.adapter = secondAdapter
        binding.rvSecondRecommendation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun makeList(): List<Menu> {
        return listOf(
            Menu(
                menuId = "MN001",
                imageId = R.drawable.a1,
                menu = "Nasi Goreng",
                restaurant = "Warung Mak Nyak",
                restaurantId = 101,
                restaurantAddress = "Jl. Mayor Salim Batubara No. 18, Palembang",
                lat = -2.990934,
                lon = 104.742344,
                stock = 20,
                rating = 4.5,
                distance = 2.0,
                price = 15000,
                expireDate = "27 Mei 2025"
            ),
            Menu(
                menuId = "MN002",
                imageId = R.drawable.a2,
                menu = "Pempek Kapal Selam",
                restaurant = "Pempek Pak Raden",
                restaurantId = 102,
                restaurantAddress = "Jl. Demang Lebar Daun No. 5, Palembang",
                lat = -2.974945,
                lon = 104.742887,
                stock = 15,
                rating = 4.8,
                distance = 1.5,
                price = 25000,
                expireDate = "27 Mei 2025"// 25 December 2024
            ),
            Menu(
                menuId = "MN003",
                imageId = R.drawable.a3,
                menu = "Mie Ayam",
                restaurant = "Mie Ayam Jago",
                restaurantId = 103,
                restaurantAddress = "Jl. Pangeran Antasari No. 10, Palembang",
                lat = -2.9765,
                lon = 104.7345,
                stock = 25,
                rating = 4.3,
                distance = 3.5,
                price = 12000,
                expireDate = "27 Mei 2025" // 10 January 2025
            ),
            Menu(
                menuId = "MN004",
                imageId = R.drawable.a4,
                menu = "Sate Padang",
                restaurant = "Sate Padang Ajo",
                restaurantId = 104,
                restaurantAddress = "Jl. Kapt. A. Rivai No. 2, Palembang",
                lat = -2.9867,
                lon = 104.7389,
                stock = 10,
                rating = 4.6,
                distance = 4.0,
                price = 30000,
                expireDate = "27 Mei 2025" // 20 December 2024
            ),
            Menu(
                menuId = "MN005",
                imageId = R.drawable.a5,
                menu = "Ayam Bakar",
                restaurant = "Ayam Bakar Wong Solo",
                restaurantId = 105,
                restaurantAddress = "Jl. Basuki Rahmat No. 8, Palembang",
                lat = -2.9932,
                lon = 104.7396,
                stock = 30,
                rating = 4.7,
                distance = 2.8,
                price = 35000,
                expireDate = "27 Mei 2025"// 5 February 2025
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
}