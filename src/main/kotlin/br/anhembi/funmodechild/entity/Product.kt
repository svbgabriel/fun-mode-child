package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.model.response.ProductResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.LocalDateTime

@Document("products")
data class Product(
    @Id
    val id: String? = null,
    val sku: Int,
    val name: String,
    val description: String,
    val price: Double = 0.0,
    val promoted: Boolean = false,
    @DocumentReference(lazy = true)
    val category: Category,
    val reference: String,
    val referenceBig: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val quantity: Int = 0
) {
    fun toApiResponse() = ProductResponse(
        this.id!!,
        this.sku,
        this.name,
        this.description,
        this.price,
        this.promoted,
        this.category.toApiResponse(),
        this.createdAt,
        this.quantity
    )
}
