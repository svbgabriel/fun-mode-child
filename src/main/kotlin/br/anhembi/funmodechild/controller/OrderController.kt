package br.anhembi.funmodechild.controller

import br.anhembi.funmodechild.entity.OrderStatus
import br.anhembi.funmodechild.model.request.OrderRequest
import br.anhembi.funmodechild.model.response.OrderResponse
import br.anhembi.funmodechild.service.OrderService
import br.anhembi.funmodechild.util.AuthenticationUtil.getLoggedUser
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Order processing and history")
class OrderController(private val orderService: OrderService) {

    @GetMapping("/customer")
    @Operation(summary = "Find all orders for the logged customer")
    fun findOrdersByCustomer(): List<OrderResponse> {
        val customer = getLoggedUser()

        return orderService.findOrdersByUserId(customer.id)
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order details by ID")
    @ApiResponse(responseCode = "200", description = "Order found")
    @ApiResponse(responseCode = "404", description = "Order not found")
    fun findOrderById(@PathVariable("orderId") id: String): OrderResponse {
        val loggedUser = getLoggedUser()

        return orderService.findOrderById(id, loggedUser.id)
    }

    @PutMapping("/{orderId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Cancel an order")
    @ApiResponse(responseCode = "204", description = "Order cancelled successfully")
    @ApiResponse(responseCode = "404", description = "Order not found")
    fun cancelOrderById(@PathVariable("orderId") id: String) {
        val loggedUser = getLoggedUser()

        orderService.updateStatus(id, OrderStatus.CANCELLED, loggedUser.id)
    }

    @PostMapping
    @Operation(summary = "Create a new order")
    @ApiResponse(responseCode = "200", description = "Order created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid order data")
    fun createOrder(@Valid @RequestBody request: OrderRequest): OrderResponse {
        val loggedUser = getLoggedUser()

        return orderService.createOrder(request, loggedUser.id)
    }
}
