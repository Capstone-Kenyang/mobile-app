package com.example.kenyang.data.dataclass

data class Menu(
    val menuId: String,
    val imageId: Int,
    val menu: String,
    val restaurant: String,
    val restaurantId: Int,
    val restaurantAddress: String,
    val lat: Double,
    val lon: Double,
    val stock: Int,
    val rating: Double,
    val distance: Double,
    val price: Int,
)
