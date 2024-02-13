package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.model.common.LoggedUser
import org.bson.BsonObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CustomerTest {

    @Test
    fun `should create a LoggedUser from Customer`() {
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

        val expected = LoggedUser(
            id = id,
            email = "test@test.com",
            password = "not_real*9",
            accountNonLocked = true,
            accountNonExpired = true,
            credentialsNonExpired = true,
            enabled = true,
        )


        assertEquals(expected, customer.toLoggedUser())
    }
}
