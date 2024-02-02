package br.anhembi.funmodechild.model.response

import java.time.LocalDateTime

data class PaymentResponse(
    val id: String,
    val createdAt: LocalDateTime,
    val statements: Int,
    val totalPrice: Double
)
