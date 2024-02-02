package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.model.common.PaymentException
import br.anhembi.funmodechild.model.request.PaymentRequest
import br.anhembi.funmodechild.model.response.PaymentResponse
import br.anhembi.funmodechild.repository.OrderRepository
import br.anhembi.funmodechild.repository.PaymentRepository
import org.springframework.stereotype.Service

@Service
class PaymentService(private val paymentRepository: PaymentRepository, private val orderRepository: OrderRepository) {

    fun makePayment(request: PaymentRequest, orderId: String, customerId: String): PaymentResponse {
        val order = orderRepository.findByIdAndCustomerId(orderId, customerId)
            ?: throw PaymentException("Order doesn't match the customer")

        val payment = request.toDoc(order)

        val savedPayment = paymentRepository.save(payment)

        return savedPayment.toApiResponse()
    }
}
