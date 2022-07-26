package com.aeri77.mylearn.screen.home.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class ShopsItemsModel(
    val name: String,
    val price: String,
    val isWishlist: Boolean = false,
    val isFavorite: Boolean = false,
    val cakeSize: List<CakeSize>,
    val toppings: List<Toppings>,
    val category: CategoriesName
)

data class CategoriesName(
    val name: String,
    val isSelected: Boolean = false
//    val color: Color,
//    val icons: ImageVector
)

data class CakeSize(
    val name: String,
    val size: Int
)

data class Toppings(
    val name: String,
    val color : Color
)


/** Dummy Model **/

fun getAllCakes(): List<ShopsItemsModel> {
    return listOf(
        ShopsItemsModel()
    )
}

fun getAllCategories(): List<CategoriesName> {
    return listOf(
        CategoriesName("Butter"),
        CategoriesName("Sponge"),
        CategoriesName("Pound"),
        CategoriesName("Genoise"),
        CategoriesName("Biscuit"),
        CategoriesName("Angel Food"),
        CategoriesName("Chiffon"),
    )
}

fun getAllSize(): List<CakeSize>{
    return listOf(
        CakeSize("10cm", 10),
        CakeSize("12cm", 12),
        CakeSize("16cm", 16),
        CakeSize("20cm", 20),
    )
}

fun getAlToppings(): List<Toppings>{
    return listOf(
        Toppings("Chocolate",)
    )
}