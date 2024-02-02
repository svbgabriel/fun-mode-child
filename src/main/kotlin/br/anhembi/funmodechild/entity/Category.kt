package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.model.response.CategoryResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("categories")
data class Category(
    @Id
    val id: String? = null,
    val name: String
) {
    fun toApiResponse() = CategoryResponse(
        id = this.id!!,
        name = this.name
    )
}
