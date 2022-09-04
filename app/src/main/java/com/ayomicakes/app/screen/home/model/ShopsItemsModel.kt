package com.ayomicakes.app.screen.home.model

data class ShopsItemsModel(
    val name: CakesName,
    val price: String,
    val isWishlist: Boolean = false,
    val isFavorite: Boolean = false,
    val cakeSize: List<SizeCake>,
    val toppings: List<Toppings>
)

/** Enums **/

enum class CakesName(
    val names: String
) {
    BUTTER("Butter Cake"),
    SPONGE("Sponge Cake"),
    POUND("Pound Cake"),
    GENOISE("Genoise Cake"),
    BISCUIT("Biscuit Cake"),
    ANGEL_FOOD("Angel Food Cake"),
    CHIFFON("Chiffon Cake"),
}

enum class Toppings(
    val names: String
) {
    CHOCOLATE("Chocolates"),
    CHEESE("Cheeses"),
    FRUITS("Fruits"),
    OREOS("Oreos")
}


enum class SizeCake(val idx: Int, val cm: String, val size: Int) {
    SMALL(0, "10cm", 10),
    MEDIUM(1, "12cm", 12),
    LARGE(2, "16cm", 16),
    EXTRA_LARGE(3, "20cm", 20)
}

/** Dummy Model **/

fun getAllCakes(): List<ShopsItemsModel> {
    return listOf(
        ShopsItemsModel(
            name = CakesName.SPONGE,
            price = "Rp. 60,000",
            cakeSize = getSomeSize(
                SizeCake.MEDIUM, SizeCake.SMALL, SizeCake.LARGE
            ),
            toppings = getSomeToppings(
                Toppings.CHOCOLATE, Toppings.CHEESE
            )
        ),
        ShopsItemsModel(
            name = CakesName.BISCUIT,
            price = "Rp. 65,000",
            cakeSize = getSomeSize(
                SizeCake.MEDIUM, SizeCake.SMALL, SizeCake.LARGE, SizeCake.EXTRA_LARGE
            ),
            toppings = getSomeToppings(
                Toppings.CHOCOLATE, Toppings.CHEESE, Toppings.OREOS
            )
        ),
        ShopsItemsModel(
            name = CakesName.BUTTER,
            price = "Rp. 70,000",
            cakeSize = getSomeSize(
                SizeCake.MEDIUM, SizeCake.SMALL, SizeCake.LARGE
            ),
            toppings = getSomeToppings(
                Toppings.CHOCOLATE, Toppings.CHEESE, Toppings.FRUITS
            )
        ),
        ShopsItemsModel(
            name = CakesName.SPONGE,
            price = "Rp. 60,000",
            cakeSize = getSomeSize(
                SizeCake.MEDIUM, SizeCake.SMALL
            ),
            toppings = getSomeToppings(
                Toppings.CHOCOLATE, Toppings.CHEESE
            )
        ),
    )
}

fun getAllCategories(): List<CakesName> {
    return listOf(
        CakesName.CHIFFON,
        CakesName.ANGEL_FOOD,
        CakesName.BUTTER,
        CakesName.BISCUIT,
        CakesName.GENOISE,
        CakesName.POUND,
        CakesName.SPONGE
    )
}

fun getAllSize(): List<SizeCake> {
    return listOf(
        SizeCake.SMALL, SizeCake.LARGE, SizeCake.MEDIUM, SizeCake.EXTRA_LARGE
    )
}

fun getSomeSize(vararg size: SizeCake): List<SizeCake> {
    return size.asList()
}

fun getAllToppings(): List<Toppings> {
    return listOf(
        Toppings.CHOCOLATE, Toppings.CHEESE, Toppings.FRUITS, Toppings.OREOS
    )
}

fun getSomeToppings(vararg toppings: Toppings): List<Toppings> {
    return toppings.asList()
}