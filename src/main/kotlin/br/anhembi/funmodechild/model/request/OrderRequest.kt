package br.anhembi.funmodechild.model.request

@JvmRecord
data class OrderRequest(
    @JvmField val details: List<OrderDetailRequest> = emptyList()
)
