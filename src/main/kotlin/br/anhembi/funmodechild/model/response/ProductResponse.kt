package br.anhembi.funmodechild.model.response

import java.time.LocalDateTime

data class ProductResponse(
    val id: String,
    val sku: Int,
    val name: String,
    val description: String,
    val price: Double,
    val promoted: Boolean,
    val category: CategoryResponse,
    val createdAt: LocalDateTime,
    val quantity: Int
)
