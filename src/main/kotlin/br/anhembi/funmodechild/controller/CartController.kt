package br.anhembi.funmodechild.controller

import br.anhembi.funmodechild.model.request.CartRequest
import br.anhembi.funmodechild.model.response.CartResponse
import br.anhembi.funmodechild.service.CartService
import br.anhembi.funmodechild.util.AuthenticationUtil.getLoggedUser
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart", description = "Shopping cart management")
class CartController(private val cartService: CartService) {

    @GetMapping
    @Operation(summary = "Get current user's cart")
    fun getCart(): CartResponse {
        val user = getLoggedUser()
        return cartService.getCartByCustomer(user.id)
    }

    @PostMapping("/items")
    @Operation(summary = "Add or update item in cart")
    fun addItem(@Valid @RequestBody request: CartRequest): CartResponse {
        val user = getLoggedUser()
        return cartService.addItem(user.id, request.productId, request.quantity)
    }

    @DeleteMapping("/items/{productId}")
    @Operation(summary = "Remove item from cart")
    fun removeItem(@PathVariable productId: String): CartResponse {
        val user = getLoggedUser()
        return cartService.removeItem(user.id, productId)
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Clear cart")
    fun clearCart() {
        val user = getLoggedUser()
        cartService.clearCart(user.id)
    }
}
