package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.entity.Order
import br.anhembi.funmodechild.entity.Order.OrderDetail
import br.anhembi.funmodechild.entity.OrderStatus
import br.anhembi.funmodechild.model.common.OrderNotFoundException
import br.anhembi.funmodechild.model.request.OrderRequest
import br.anhembi.funmodechild.model.response.OrderResponse
import br.anhembi.funmodechild.repository.CustomerRepository
import br.anhembi.funmodechild.repository.OrderRepository
import br.anhembi.funmodechild.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun findOrderById(id: String, customerId: String): OrderResponse =
        orderRepository.findByIdAndCustomerId(id, customerId)?.toApiResponse()
            ?: throw OrderNotFoundException("Order not found").also {
                logger.warn("Order not found: $id for customer: $customerId")
            }

    fun updateStatus(id: String, status: OrderStatus, customerId: String) {
        val order = orderRepository.findByIdAndCustomerId(id, customerId)
            ?: throw OrderNotFoundException("Order not found").also {
                logger.warn("Order not found for status update: $id for customer: $customerId")
            }

        order.status = status
        orderRepository.save(order)
        logger.info("Order $id status updated to $status by customer $customerId")
    }

    fun findOrdersByUserId(customerId: String): List<OrderResponse> =
        orderRepository.findByCustomerId(customerId)
            .map { it.toApiResponse() }

    @Transactional
    fun createOrder(request: OrderRequest, id: String): OrderResponse {
        logger.info("Creating order for customer $id")
        
        val orderDetails = request.details.map { detailRequest ->
            val product = productRepository.findById(detailRequest.productId).orElseThrow {
                logger.error("Product ${detailRequest.productId} not found during order creation")
                NoSuchElementException("Product not found")
            }

            if (product.quantity < detailRequest.quantity) {
                logger.warn("Insufficient stock for product ${product.name}: requested ${detailRequest.quantity}, available ${product.quantity}")
                throw IllegalStateException("Insufficient stock for product ${product.name}")
            }

            // Deduct stock
            product.quantity -= detailRequest.quantity
            productRepository.save(product)
            
            OrderDetail(product, product.price, detailRequest.quantity)
        }

        val customer = customerRepository.findById(id).orElseThrow {
            logger.error("Customer $id not found during order creation")
            NoSuchElementException("Customer not found")
        }

        val totalPrice = orderDetails.sumOf { it.price * it.quantity }

        val order = Order(
            createdAt = LocalDateTime.now(),
            customer = customer,
            details = orderDetails,
            totalPrice = totalPrice,
            status = OrderStatus.PENDING
        )

        val savedOrder = orderRepository.save(order)
        logger.info("Order ${savedOrder.id} created successfully for customer $id with status ${savedOrder.status}")

        return savedOrder.toApiResponse()
    }
}
