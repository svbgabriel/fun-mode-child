package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.customer
import br.anhembi.funmodechild.model.common.LoggedUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CustomerTest {

    @Test
    fun `should create a LoggedUser from Customer`() {
        val expected = LoggedUser(
            id = customer.id!!,
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
