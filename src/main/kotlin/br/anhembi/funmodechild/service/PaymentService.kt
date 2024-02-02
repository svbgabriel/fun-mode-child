package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.entity.Payment
import br.anhembi.funmodechild.model.common.PaymentException
import br.anhembi.funmodechild.model.request.PaymentRequest
import br.anhembi.funmodechild.model.response.PaymentResponse
import br.anhembi.funmodechild.repository.OrderRepository
import br.anhembi.funmodechild.repository.PaymentRepository
import org.springframework.stereotype.Service

@Service
class PaymentService(private val paymentRepository: PaymentRepository, private val orderRepository: OrderRepository) {
    private fun save(payment: Payment, orderId: String, customerId: String): Payment {
        val order = orderRepository.findByIdAndCustomerId(orderId, customerId)
            ?: throw PaymentException("Order doesn't match the customer")

        // Completa os dados de pagamento
        payment.order = order

        // Salva o pagamento
        paymentRepository.save(payment)

        return payment
    }

    fun makePayment(request: PaymentRequest, orderId: String, customerId: String): PaymentResponse {
        // Salva os dados de pagamento
        val payment = request.toDoc()

        val paymentResposta = save(payment, orderId, customerId)

        return PaymentResponse(paymentResposta)
    }
}
