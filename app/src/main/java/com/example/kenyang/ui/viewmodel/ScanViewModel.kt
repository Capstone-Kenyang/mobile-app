package com.example.kenyang.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kenyang.data.remote.ApiConfig
import com.example.kenyang.data.remote.PredictResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class ScanViewModel : ViewModel() {
    private val _scanResult = MutableLiveData<String>()
    val scanResult = _scanResult
    fun upload(multipartBody: MultipartBody.Part) {
        val client = ApiConfig.getApiService().predictImage(multipartBody)
        client.enqueue(object : Callback<PredictResponse> {
            override fun onResponse(call: Call<PredictResponse>, response: Response<PredictResponse>) {
                if (response.isSuccessful) {
                    _scanResult.value = response.body()?.predict
                    Log.d(TAG, response.body().toString())
                } else {
                    Log.e(TAG, "Error: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
            }

        })
    }

    companion object {
        const val TAG = "ScanViewModel"
    }
}
