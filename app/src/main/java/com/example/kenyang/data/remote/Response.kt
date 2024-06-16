package com.example.kenyang.data.remote

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("predict")
	val predict: String
)
