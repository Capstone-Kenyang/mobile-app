package com.example.kenyang.ui.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kenyang.R
import com.example.kenyang.converter.uriToFile
import com.example.kenyang.databinding.ActivityScanBinding
import com.example.kenyang.ui.viewmodel.ScanViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class ScanActivity : AppCompatActivity() {

    private val binding: ActivityScanBinding by lazy {
        ActivityScanBinding.inflate(layoutInflater)
    }

    private val scanViewModel: ScanViewModel by viewModels<ScanViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val uri = Uri.parse(intent.getStringExtra("imageUri"))
        binding.ivImageResult.setImageURI(uri)

        binding.btnPredict.setOnClickListener {
            uploadImage(uri)
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        scanViewModel.scanResult.observe(this) {
            showResult(it)
        }

    }

    private fun uploadImage(currentImageUri: Uri) {
        currentImageUri.let { uri ->

            val imageFile = uriToFile(uri, this)
            Log.d("Image File", "showImage: ${imageFile.path}")


            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestImageFile
            )

            scanViewModel.upload(multipartBody)
        }
    }

    private fun showResult(result: String) {
        binding.btnPredict.visibility = View.INVISIBLE
        binding.tvPredictResult.visibility = View.VISIBLE
        binding.tvResultDescription.visibility = View.VISIBLE

        binding.tvPredictResult.text = result
        Log.d("Res", result)

        val description = if (result.contains("fresh") || result.contains("Fresh")) {
            "Kemungkinan aman dikonsumsi"
        } else {
            "Kemungkinan tidak aman dikonsumsi"
        }

        binding.tvResultDescription.text = description
    }

}