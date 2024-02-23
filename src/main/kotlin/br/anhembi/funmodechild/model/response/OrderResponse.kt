package br.anhembi.funmodechild.model.response

import br.anhembi.funmodechild.entity.OrderStatus
import java.time.LocalDateTime

data class OrderResponse(
    val id: String,
    val createdAt: LocalDateTime,
    val details: List<OrderDetailResponse>,
    val totalPrice: Double,
    val status: OrderStatus
)
