package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.entity.Cart
import br.anhembi.funmodechild.entity.Cart.CartItem
import br.anhembi.funmodechild.model.response.CartResponse
import br.anhembi.funmodechild.repository.CartRepository
import br.anhembi.funmodechild.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val productService: ProductService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun getCartByCustomer(customerId: String): CartResponse {
        val cart = cartRepository.findByCustomerId(customerId) ?: Cart(customerId = customerId)
        return mapToResponse(cart)
    }

    fun addItem(customerId: String, productId: String, quantity: Int): CartResponse {
        logger.info("Adding item $productId to cart for customer $customerId")
        val cart = cartRepository.findByCustomerId(customerId) ?: Cart(customerId = customerId)
        
        val existingItem = cart.items.find { it.productId == productId }
        if (existingItem != null) {
            existingItem.quantity += quantity
        } else {
            cart.items.add(CartItem(productId, quantity))
        }
        
        cart.updatedAt = LocalDateTime.now()
        val saved = cartRepository.save(cart)
        return mapToResponse(saved)
    }

    fun removeItem(customerId: String, productId: String): CartResponse {
        logger.info("Removing item $productId from cart for customer $customerId")
        val cart = cartRepository.findByCustomerId(customerId) ?: return getCartByCustomer(customerId)
        
        cart.items.removeIf { it.productId == productId }
        cart.updatedAt = LocalDateTime.now()
        val saved = cartRepository.save(cart)
        return mapToResponse(saved)
    }

    fun clearCart(customerId: String) {
        logger.info("Clearing cart for customer $customerId")
        cartRepository.deleteByCustomerId(customerId)
    }

    private fun mapToResponse(cart: Cart): CartResponse {
        val items = cart.items.map { item ->
            val productResponse = productService.findProductById(item.productId)
            CartResponse.CartItemResponse(productResponse, item.quantity)
        }
        return CartResponse(cart.customerId, items, cart.updatedAt)
    }
}
