package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.customer
import br.anhembi.funmodechild.entity.OrderStatus
import br.anhembi.funmodechild.model.common.OrderNotFoundException
import br.anhembi.funmodechild.model.request.OrderDetailRequest
import br.anhembi.funmodechild.model.request.OrderRequest
import br.anhembi.funmodechild.order
import br.anhembi.funmodechild.product
import br.anhembi.funmodechild.repository.CustomerRepository
import br.anhembi.funmodechild.repository.OrderRepository
import br.anhembi.funmodechild.repository.ProductRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class OrderServiceTest {

    @MockK
    private lateinit var orderRepository: OrderRepository

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var productRepository: ProductRepository

    @InjectMockKs
    private lateinit var orderService: OrderService

    @Test
    fun `should get a order with a valid orderId and customerId`() {
        val orderId = order.id!!
        val customerId = customer.id!!

        every { orderRepository.findByIdAndCustomerId(orderId, customerId) } returns order

        val result = orderService.findOrderById(orderId, customerId)

        assertEquals(order.toApiResponse(), result)
    }

    @Test
    fun `should throw exception when order not found`() {
        every { orderRepository.findByIdAndCustomerId(any(), any()) } returns null

        assertThrows<OrderNotFoundException> {
            orderService.findOrderById("invalid", "invalid")
        }
    }

    @Test
    fun `should create order successfully and deduct stock`() {
        val testProduct = product.copy()
        val testCustomer = customer.copy()
        val request = OrderRequest(
            details = listOf(OrderDetailRequest(productId = testProduct.id!!, quantity = 2))
        )
        val customerId = testCustomer.id!!
        val initialStock = testProduct.quantity

        every { productRepository.findById(testProduct.id!!) } returns Optional.of(testProduct)
        every { customerRepository.findById(customerId) } returns Optional.of(testCustomer)
        every { productRepository.save(any()) } returns testProduct
        every { orderRepository.save(any()) } returns order

        val result = orderService.createOrder(request, customerId)

        verify { productRepository.save(match { it.quantity == initialStock - 2 }) }
        verify { orderRepository.save(match { it.status == OrderStatus.PENDING }) }
        assertEquals(order.toApiResponse(), result)
    }

    @Test
    fun `should throw exception when stock is insufficient`() {
        val request = OrderRequest(
            details = listOf(OrderDetailRequest(productId = product.id!!, quantity = 100))
        )
        val customerId = customer.id!!

        every { productRepository.findById(product.id!!) } returns Optional.of(product.copy(quantity = 10))

        assertThrows<IllegalStateException> {
            orderService.createOrder(request, customerId)
        }
    }

    @Test
    fun `should update order status successfully`() {
        val orderId = order.id!!
        val customerId = customer.id!!

        every { orderRepository.findByIdAndCustomerId(orderId, customerId) } returns order
        every { orderRepository.save(any()) } returns order

        orderService.updateStatus(orderId, OrderStatus.SHIPPED, customerId)

        verify { orderRepository.save(match { it.status == OrderStatus.SHIPPED }) }
    }
}
