package br.anhembi.funmodechild.model.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class CartRequest(
    @field:NotBlank(message = "Product ID is required")
    val productId: String,
    
    @field:Min(value = 1, message = "Quantity must be at least 1")
    val quantity: Int
)
