package io.seroo.model

import io.seroo.model.enum.ProductGroupType

data class ProductGroup(
    val id: String,
    val name: String,
    val groupType: ProductGroupType
)