package br.anhembi.funmodechild.model.request

import br.anhembi.funmodechild.entity.Order
import br.anhembi.funmodechild.entity.Payment
import java.time.LocalDateTime

@JvmRecord
data class PaymentRequest(
    @JvmField val cardNumber: String,
    @JvmField val cardName: String,
    @JvmField val month: Int,
    @JvmField val year: Int,
    @JvmField val cvv: Int,
    @JvmField val statements: Int
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
