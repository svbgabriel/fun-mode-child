package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.model.response.CategoryResponse
import br.anhembi.funmodechild.model.response.OrderDetailResponse
import br.anhembi.funmodechild.model.response.OrderResponse
import br.anhembi.funmodechild.model.response.ProductResponse
import br.anhembi.funmodechild.order
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class OrderTest {

    @Test
    fun `should create a OrderResponse from Order`() {
        val product = order.details.first().product

        val expected = OrderResponse(
            id = order.id!!,
            active = true,
            createdAt = order.createdAt,
            totalPrice = 50.0,
            details = listOf(
                OrderDetailResponse(
                    product = ProductResponse(
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
                    ),
                    price = 50.0,
                    quantity = 1
                )
            )
        )

        assertEquals(expected, order.toApiResponse())
    }
}
