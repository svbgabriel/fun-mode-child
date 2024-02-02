package br.anhembi.funmodechild.model.request

data class OrderDetailRequest(
    val productId: String,
    val quantity: Int
)
