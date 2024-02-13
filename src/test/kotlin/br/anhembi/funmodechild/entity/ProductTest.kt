package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.model.response.CategoryResponse
import br.anhembi.funmodechild.model.response.ProductResponse
import org.bson.BsonObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ProductTest {

    @Test
    fun `should create a ProductResponse from Product`() {
        val productId = BsonObjectId().toString()
        val productDate = LocalDateTime.now()
        val categoryId = BsonObjectId().toString()

        val product = Product(
            id = productId,
            sku = 1,
            name = "Product",
            description = "Some product",
            price = 50.0,
            reference = "",
            referenceBig = "",
            quantity = 1,
            promoted = false,
            createdAt = productDate,
            category = Category(
                id = categoryId,
                name = "Category Name"
            )
        )

        val expected = ProductResponse(
            id = productId,
            sku = 1,
            name = "Product",
            description = "Some product",
            price = 50.0,
            category = CategoryResponse(
                id = categoryId,
                name = "Category Name"
            ),
            promoted = false,
            quantity = 1,
            createdAt = productDate
        )

        assertEquals(expected, product.toApiResponse())
    }
}
