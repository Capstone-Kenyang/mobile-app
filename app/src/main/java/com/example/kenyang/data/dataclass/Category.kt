package com.example.kenyang.data.dataclass

data class Category(
    val id: Int,
    val name: String,
    val image: Int,
    val listMenu: List<Menu>
)
