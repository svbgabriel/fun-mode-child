package br.anhembi.funmodechild.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.Date

@Document("carts")
data class Cart(
    @Id
    val id: String? = null,
    
    @Indexed(unique = true)
    val customerId: String,
    
    var items: MutableList<CartItem> = mutableListOf(),
    
    @Indexed(expireAfter = "24h")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    data class CartItem(
        val productId: String,
        var quantity: Int
    )
}
