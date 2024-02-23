package br.anhembi.funmodechild.model.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty

data class OrderRequest(
    @field:NotEmpty(message = "Order must have at least one item")
    @field:Valid
    val details: List<OrderDetailRequest> = emptyList()
)
