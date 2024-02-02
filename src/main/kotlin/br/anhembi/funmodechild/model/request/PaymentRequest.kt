package br.anhembi.funmodechild.model.request

import br.anhembi.funmodechild.entity.Order
import br.anhembi.funmodechild.entity.Payment
import java.time.LocalDateTime

data class PaymentRequest(
    val cardNumber: String,
    val cardName: String,
    val month: Int,
    val year: Int,
    val cvv: Int,
    val statements: Int
) {
    fun toDoc(order: Order? = null) = Payment(
        id = null,
        order = order,
        cardNumber = this.cardNumber,
        cardName = this.cardName,
        month = this.month,
        year = this.year,
        cvv = this.cvv,
        statements = this.statements,
        createdAt = LocalDateTime.now()
    )
}
