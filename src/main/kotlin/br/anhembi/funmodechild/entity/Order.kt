package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.model.response.OrderDetailResponse
import br.anhembi.funmodechild.model.response.OrderResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.LocalDateTime

@Document("orders")
data class Order(
    @Id
    val id: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @DocumentReference(lazy = true)
    val customer: Customer,
    val details: List<OrderDetail> = emptyList(),
    val totalPrice: Double = 0.0,
    var active: Boolean = true
) {
    fun toApiResponse() = OrderResponse(
        this.id!!,
        this.createdAt,
        details
            .map { it.toApiResponse() }
            .toList(),
        this.totalPrice,
        this.active
    )

    data class OrderDetail(
        @DocumentReference(lazy = true)
        val product: Product,
        val price: Double = 0.0,
        val quantity: Int = 0,
    ) {
        fun toApiResponse() = OrderDetailResponse(
            product = product.toApiResponse(),
            price = this.price,
            quantity = this.quantity
        )
    }
}
