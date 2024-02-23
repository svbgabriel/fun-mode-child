package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.customer
import br.anhembi.funmodechild.model.common.PasswordNotMatchException
import br.anhembi.funmodechild.model.common.UserNotFoundException
import br.anhembi.funmodechild.model.request.UserRequest
import br.anhembi.funmodechild.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class UserServiceTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @InjectMockKs
    private lateinit var userService: UserService

    @Test
    fun `should create a user successfully`() {
        val request = UserRequest(
            name = "Test",
            surname = "Test",
            email = "test@test.com",
            password = "password",
            birthDate = LocalDate.now(),
            customerIdentification = "1",
            phoneNumber = "123"
        )

        every { passwordEncoder.encode(any()) } returns "encoded_password"
        every { customerRepository.save(any()) } returns customer

        userService.create(request)

        verify { customerRepository.save(any()) }
    }

    @Test
    fun `should update password successfully`() {
        val email = customer.email
        val oldPassword = "old_password"
        val newPassword = "new_password"

        every { customerRepository.findByEmail(email) } returns customer
        every { passwordEncoder.matches(oldPassword, customer.password) } returns true
        every { passwordEncoder.encode(newPassword) } returns "new_encoded_password"
        every { customerRepository.save(any()) } returns customer

        userService.updatePassword(email, oldPassword, newPassword, newPassword)

        verify { customerRepository.save(any()) }
    }

    @Test
    fun `should throw exception when user not found on password update`() {
        every { customerRepository.findByEmail(any()) } returns null

        assertThrows<UserNotFoundException> {
            userService.updatePassword("test@test.com", "any", "any", "any")
        }
    }

    @Test
    fun `should throw exception when new passwords do not match`() {
        every { customerRepository.findByEmail(any()) } returns customer

        assertThrows<PasswordNotMatchException> {
            userService.updatePassword(customer.email, "any", "pass1", "pass2")
        }
    }

    @Test
    fun `should throw exception when old password does not match`() {
        every { customerRepository.findByEmail(any()) } returns customer
        every { passwordEncoder.matches(any(), any()) } returns false

        assertThrows<PasswordNotMatchException> {
            userService.updatePassword(customer.email, "wrong_old", "new", "new")
        }
    }
}
