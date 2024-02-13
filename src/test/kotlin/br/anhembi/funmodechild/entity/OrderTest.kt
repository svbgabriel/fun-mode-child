package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.model.response.CategoryResponse
import br.anhembi.funmodechild.model.response.OrderDetailResponse
import br.anhembi.funmodechild.model.response.OrderResponse
import br.anhembi.funmodechild.model.response.ProductResponse
import org.bson.BsonObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class OrderTest {

    @Test
    fun `should create a OrderResponse from Order`() {
        val id = BsonObjectId().toString()

        val customer = Customer(
            id = id,
            birthDate = LocalDate.now().minusYears(20),
            customerIdentification = "1",
            email = "test@test.com",
            name = "Test",
            surname = "Test",
            password = "not_real*9",
            phoneNumber = "123456789",
            accountNonLocked = true,
            accountNonExpired = true,
            credentialsNonExpired = true,
            enabled = true,
        )

        val orderDate = LocalDateTime.now()
        val categoryId = BsonObjectId().toString()
        val productId = BsonObjectId().toString()
        val productDate = LocalDateTime.now()
        val order = Order(
            id = id,
            customer = customer,
            createdAt = orderDate,
            totalPrice = 50.0,
            details = listOf(
                Order.OrderDetail(
                    quantity = 1,
                    price = 50.0,
                    product = Product(
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
                )
            )
        )

        val expected = OrderResponse(
            id = id,
            active = true,
            createdAt = orderDate,
            totalPrice = 50.0,
            details = listOf(
                OrderDetailResponse(
                    product = ProductResponse(
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
                    ),
                    price = 50.0,
                    quantity = 1
                )
            )
        )

        assertEquals(expected, order.toApiResponse())
    }
}
