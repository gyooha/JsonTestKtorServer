package io.seroo.model

data class Product(
    val id: String = "",
    val productNumber: Int = -1,
    val productName: String = "",
    val productFactory: ProductFactory? = null,
    val productGroup: ProductGroup? = null
)