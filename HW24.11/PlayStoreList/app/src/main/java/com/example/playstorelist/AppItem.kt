package com.example.playstorelist

data class AppItem(
    val iconResId: Int,
    val name: String,
    val category: String,
    val rating: Double,
    val sizeMb: Int
)