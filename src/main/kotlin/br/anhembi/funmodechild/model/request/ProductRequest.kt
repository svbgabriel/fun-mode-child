package br.anhembi.funmodechild.model.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProductRequest(
    val id: String? = null,

    @field:NotNull(message = "SKU is required")
    val sku: Int,

    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:NotBlank(message = "Description is required")
    val description: String,

    @field:Min(value = 0, message = "Price cannot be negative")
    val price: Double,

    val reference: String = "",
    val referenceBig: String = "",

    @field:Min(value = 0, message = "Quantity cannot be negative")
    val quantity: Int = 0,

    val promoted: Boolean = false,

    @field:NotBlank(message = "Category ID is required")
    val categoryId: String
)
