package br.anhembi.funmodechild.model.response

import java.time.LocalDateTime

data class CartResponse(
    val customerId: String,
    val items: List<CartItemResponse>,
    val updatedAt: LocalDateTime
) {
    data class CartItemResponse(
        val product: ProductResponse,
        val quantity: Int
    )
}
