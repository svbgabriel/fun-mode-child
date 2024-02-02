package br.anhembi.funmodechild.model.request

@JvmRecord
data class OrderDetailRequest(
    @JvmField val productId: String,
    @JvmField val quantity: Int
)
