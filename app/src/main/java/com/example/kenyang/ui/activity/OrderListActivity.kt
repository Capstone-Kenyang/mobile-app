package com.example.kenyang.ui.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kenyang.R
import com.example.kenyang.ui.adapter.OrderAdapter
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.databinding.ActivityOrderBinding
import com.example.kenyang.factory.ViewModelFactory
import com.example.kenyang.ui.viewmodel.OrderListViewModel
import de.hdodenhof.circleimageview.BuildConfig
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderListActivity : AppCompatActivity() {

    private val binding: ActivityOrderBinding by lazy {
        ActivityOrderBinding.inflate(layoutInflater)
    }

    private val orderListViewModel: OrderListViewModel by viewModels<OrderListViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private var currentImageUri: Uri? = null
    private val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(
        Date()
    )

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

        orderListViewModel.getAllOrder().observe(this@OrderListActivity) {
            if (it.isEmpty()) {
                binding.tvNoOrderYet.visibility = View.VISIBLE
                binding.rvOrder.visibility = View.GONE
            } else {
                inflateRvLayout(it.reversed())
                Log.d("OrderListActivity", it.reversed().toString())
            }
        }

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(this@OrderListActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })


        binding.bottomNavigation.selectedItemId = R.id.menu_order
        handleBottomNavAction()
    }

    private fun inflateRvLayout(list: List<Order>) {
        binding.tvNoOrderYet.visibility = View.GONE
        binding.rvOrder.visibility = View.VISIBLE

        val adapter = OrderAdapter()
        adapter.submitList(list)
        binding.rvOrder.adapter = adapter
        binding.rvOrder.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun handleBottomNavAction() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_camera -> {
                    startCamera()
                    true
                }
                R.id.menu_order -> {
                    val intent = Intent(this, OrderListActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            val intent = Intent(this, ScanActivity::class.java)
            intent.putExtra("imageUri", currentImageUri.toString())
            startActivity(intent)
        }
    }

    private fun getImageUri(context: Context): Uri {
        var uri: Uri? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/MyCamera/")
            }
            uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
        }
        return uri ?: getImageUriForPreQ(context)
    }

    private fun getImageUriForPreQ(context: Context): Uri {
        val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(filesDir, "/MyCamera/$timeStamp.jpg")
        if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()
        return FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            imageFile
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onRestart() {
        super.onRestart()
        binding.bottomNavigation.selectedItemId = R.id.menu_order
    }

}