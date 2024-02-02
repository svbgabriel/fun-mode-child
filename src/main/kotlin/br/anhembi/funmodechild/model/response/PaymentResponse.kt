package br.anhembi.funmodechild.model.response

import br.anhembi.funmodechild.entity.Payment

@JvmRecord
data class PaymentResponse(
    val paymentData: Payment
)
