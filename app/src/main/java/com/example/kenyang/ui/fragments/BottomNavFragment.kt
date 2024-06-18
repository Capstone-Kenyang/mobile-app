package com.example.kenyang.ui.fragments

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import com.example.kenyang.R
import com.example.kenyang.databinding.FragmentBottomNavBinding
import com.example.kenyang.ui.activity.MainActivity
import com.example.kenyang.ui.activity.OrderListActivity
import com.example.kenyang.ui.activity.ScanActivity
import de.hdodenhof.circleimageview.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BottomNavFragment : Fragment() {
    private lateinit var binding: FragmentBottomNavBinding

    private var currentImageUri: Uri? = null
    private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())
    private var currentNavItem = R.id.menu_home

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomNavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle navigation item selection
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    // Replace with HomeFragment
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_camera -> {
                    // Launch Camera
                    startCamera()
                    true
                }
                R.id.menu_order -> {
                    currentNavItem = R.id.menu_order
                    val intent = Intent(requireContext(), OrderListActivity::class.java)
                    startActivity(intent)
                    true
                }
                // Add other navigation items here
                else -> false
            }
        }

//        binding.bottomNavigation.selectedItemId = currentNavItem

//        binding.buttonCamera.setOnClickListener { startCamera() }
//        binding.buttonOrder.setOnClickListener {
//            val intent = Intent(requireContext(), OrderListActivity::class.java)
//            startActivity(intent)
//        }
//
//
//        binding.buttonHome.setOnClickListener {
//            val intent = Intent(requireContext(), MainActivity::class.java)
//            startActivity(intent)
//        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            val intent = Intent(requireContext(), ScanActivity::class.java)
            intent.putExtra("imageUri", currentImageUri.toString())
            startActivity(intent)
        }
    }

    fun getImageUri(context: Context): Uri {
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

    fun getImageUriForPreQ(context: Context): Uri {
        val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(filesDir, "/MyCamera/$timeStamp.jpg")
        if (imageFile.parentFile?.exists() == false) imageFile.parentFile?.mkdir()
        return FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            imageFile
        )
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    }

}