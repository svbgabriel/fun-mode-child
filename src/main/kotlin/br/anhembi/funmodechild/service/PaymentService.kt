package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.entity.OrderStatus
import br.anhembi.funmodechild.model.common.PaymentException
import br.anhembi.funmodechild.model.request.PaymentRequest
import br.anhembi.funmodechild.model.response.PaymentResponse
import br.anhembi.funmodechild.repository.OrderRepository
import br.anhembi.funmodechild.repository.PaymentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentService(private val paymentRepository: PaymentRepository, private val orderRepository: OrderRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun makePayment(request: PaymentRequest, orderId: String, customerId: String): PaymentResponse {
        logger.info("Processing payment for order $orderId by customer $customerId")
        val order = orderRepository.findByIdAndCustomerId(orderId, customerId)
            ?: throw PaymentException("Order doesn't match the customer").also {
                logger.error("Payment failed: Order $orderId does not belong to customer $customerId")
            }

        if (order.status != OrderStatus.PENDING) {
            throw PaymentException("Order is not in PENDING state. Current status: ${order.status}")
        }

        // Simulate successful payment processing logic here
        
        val payment = request.toDoc(order)
        val savedPayment = paymentRepository.save(payment)
        
        // Update order status to PAID
        order.status = OrderStatus.PAID
        orderRepository.save(order)
        
        logger.info("Payment ${savedPayment.id} processed successfully for order $orderId. Order status updated to PAID.")

        return savedPayment.toApiResponse()
    }
}
