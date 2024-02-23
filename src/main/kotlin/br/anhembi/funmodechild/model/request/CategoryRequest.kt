package br.anhembi.funmodechild.model.request

import jakarta.validation.constraints.NotBlank

data class CategoryRequest(
    val id: String? = null,
    
    @field:NotBlank(message = "Category name is required")
    val name: String
)
