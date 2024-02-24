package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.model.response.CategoryResponse
import br.anhembi.funmodechild.model.response.ProductResponse
import br.anhembi.funmodechild.product
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ProductTest {

    @Test
    fun `should create a ProductResponse from Product`() {
        val expected = ProductResponse(
            id = product.id!!,
            sku = 1,
            name = "Product",
            description = "Some product",
            price = 50.0,
            category = CategoryResponse(
                id = product.category.id!!,
                name = "Category Name"
            ),
            promoted = false,
            quantity = 1,
            createdAt = product.createdAt
        )

        assertEquals(expected, product.toApiResponse())
    }
}
