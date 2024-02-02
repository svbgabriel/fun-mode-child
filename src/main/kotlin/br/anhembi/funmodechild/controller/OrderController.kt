package br.anhembi.funmodechild.controller

import br.anhembi.funmodechild.model.request.OrderRequest
import br.anhembi.funmodechild.model.response.OrderResponse
import br.anhembi.funmodechild.service.OrderService
import br.anhembi.funmodechild.util.AuthenticationUtil.getLoggedUser
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders")
class OrderController(private val orderService: OrderService) {

    @GetMapping("/customer")
    fun findOrdersByCustomer(): List<OrderResponse> {
        val customer = getLoggedUser()

        return orderService.findOrdersByUserId(customer.id)
    }

    @GetMapping("/{orderId}")
    fun findOrderById(@PathVariable("orderId") id: String): OrderResponse {
        val loggedUser = getLoggedUser()

        return orderService.findOrderById(id, loggedUser.id)
    }

    @PutMapping("/{orderId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun cancelOrderById(@PathVariable("orderId") id: String) {
        val loggedUser = getLoggedUser()

        orderService.updateStatus(id,false, loggedUser.id)
    }

    @PostMapping
    fun createOrder(@RequestBody request: OrderRequest): OrderResponse {
        val loggedUser = getLoggedUser()

        return orderService.createOrder(request, loggedUser.id)
    }
}
