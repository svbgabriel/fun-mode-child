package br.anhembi.funmodechild.model.request

data class OrderRequest(
    val details: List<OrderDetailRequest> = emptyList()
)
