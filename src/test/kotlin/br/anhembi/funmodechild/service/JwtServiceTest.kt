package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.entity.Role
import br.anhembi.funmodechild.model.common.LoggedUser
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.util.ReflectionTestUtils
import java.util.*

@ExtendWith(MockKExtension::class)
class JwtServiceTest {

    @InjectMockKs
    private lateinit var jwtService: JwtService

    private val secret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970" // 256-bit secret

    @BeforeEach
    fun setup() {
        ReflectionTestUtils.setField(jwtService, "secret", secret)
    }

    @Test
    fun `should generate a token containing roles`() {
        val userDetails = LoggedUser(
            id = "1",
            email = "admin@test.com",
            password = "password",
            enabled = true,
            accountNonExpired = true,
            credentialsNonExpired = true,
            accountNonLocked = true,
            roles = setOf(Role.ROLE_ADMIN, Role.ROLE_CUSTOMER)
        )

        val token = jwtService.generateToken(userDetails)
        assertNotNull(token)

        val username = jwtService.extractUsername(token)
        assertEquals(userDetails.username, username)
        
        // Validar se o token não está expirado
        assertTrue(jwtService.validateToken(token, userDetails))
    }
}
