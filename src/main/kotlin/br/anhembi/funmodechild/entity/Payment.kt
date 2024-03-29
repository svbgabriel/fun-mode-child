package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.model.response.PaymentResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.LocalDateTime

@Document("payments")
data class Payment(
    @Id
    val id: String? = null,
    @DocumentReference(lazy = true)
    var order: Order? = null,
    val cardNumber: String,
    val cardName: String,
    val month: Int,
    val year: Int,
    val cvv: Int,
    val statements: Int,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun toApiResponse() = PaymentResponse(
        id = this.id!!,
        createdAt = this.createdAt,
        statements = this.statements,
        totalPrice = this.order!!.totalPrice
    )
}
