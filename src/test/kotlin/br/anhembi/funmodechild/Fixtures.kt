package br.anhembi.funmodechild

import br.anhembi.funmodechild.entity.*
import org.bson.BsonObjectId
import java.time.LocalDate
import java.time.LocalDateTime

val category = Category(
    id = BsonObjectId().toString(),
    name = "Category Name"
)

val product = Product(
    id = BsonObjectId().toString(),
    sku = 1,
    name = "Product",
    description = "Some product",
    price = 50.0,
    reference = "",
    referenceBig = "",
    quantity = 1,
    promoted = false,
    createdAt = LocalDateTime.now(),
    category = category
)

val customer = Customer(
    id = BsonObjectId().toString(),
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

val order = Order(
    id = BsonObjectId().toString(),
    customer = customer,
    createdAt = LocalDateTime.now(),
    totalPrice = 50.0,
    details = listOf(
        Order.OrderDetail(
            quantity = 1,
            price = 50.0,
            product = product
        )
    )
)

val payment = Payment(
    id = BsonObjectId().toString(),
    order = order,
    createdAt = LocalDateTime.now(),
    cardName = "",
    cardNumber = "",
    cvv = 0,
    month = 1,
    year = LocalDateTime.now().year,
    statements = 1
)
