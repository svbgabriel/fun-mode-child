package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.model.response.PaymentResponse
import br.anhembi.funmodechild.payment
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PaymentTest {

    @Test
    fun `should create a PaymentResponse from Payment`() {
        val expected = PaymentResponse(
            id = payment.id!!,
            statements = 1,
            totalPrice = 50.0,
            createdAt = payment.createdAt
        )

        assertEquals(expected, payment.toApiResponse())
    }
}
