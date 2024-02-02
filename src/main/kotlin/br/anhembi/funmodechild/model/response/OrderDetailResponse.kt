package br.anhembi.funmodechild.model.response

data class OrderDetailResponse(
    val product: ProductResponse,
    val price: Double,
    val quantity: Int
)
