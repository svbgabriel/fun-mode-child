package br.anhembi.funmodechild.model.response

@JvmRecord
data class OrderDetailResponse(
    val product: ProductResponse,
    val price: Double,
    val quantity: Int
)
