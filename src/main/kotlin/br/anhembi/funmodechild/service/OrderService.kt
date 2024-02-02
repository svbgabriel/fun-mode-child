package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.entity.Order
import br.anhembi.funmodechild.entity.Order.OrderDetail
import br.anhembi.funmodechild.model.common.OrderNotFoundException
import br.anhembi.funmodechild.model.request.OrderDetailRequest
import br.anhembi.funmodechild.model.request.OrderRequest
import br.anhembi.funmodechild.model.response.OrderResponse
import br.anhembi.funmodechild.repository.CustomerRepository
import br.anhembi.funmodechild.repository.OrderRepository
import br.anhembi.funmodechild.repository.ProductRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository
) {
    fun findOrderById(id: String, customerId: String): OrderResponse =
        orderRepository.findByIdAndCustomerId(id, customerId)?.toApiResponse()
            ?: throw OrderNotFoundException("Order not found")

    fun updateStatus(id: String, status: Boolean, customerId: String) {
        val order = orderRepository.findByIdAndCustomerId(id, customerId)
            ?: throw OrderNotFoundException("Order not found")

        order.active = status
        orderRepository.save(order)
    }

    fun findOrdersByUserId(customerId: String): List<OrderResponse> =
        orderRepository.findByCustomerId(customerId)
            .map { it.toApiResponse() }

    fun createOrder(request: OrderRequest, id: String): OrderResponse {
        val orderDetails = request.details
            .map {
                val product = productRepository.findById(it.productId).orElseThrow()
                OrderDetail(product, product.price, it.quantity)
            }

        val customer = customerRepository.findById(id).orElseThrow()

        val totalPrice = orderDetails.sumOf { it.price * it.quantity }

        val order = Order(
            createdAt = LocalDateTime.now(),
            customer = customer,
            details = orderDetails,
            totalPrice = totalPrice,
            active = true
        )

        val savedOrder = orderRepository.save(order)

        return savedOrder.toApiResponse()
    }
}
