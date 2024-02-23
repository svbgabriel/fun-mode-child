package br.anhembi.funmodechild.config

import br.anhembi.funmodechild.controller.ProductController
import br.anhembi.funmodechild.model.response.CategoryResponse
import br.anhembi.funmodechild.model.response.ProductResponse
import br.anhembi.funmodechild.service.JwtService
import br.anhembi.funmodechild.service.ProductService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(ProductController::class)
@Import(SecurityConfig::class, JwtAuthFilter::class)
class SecurityConfigTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var productService: ProductService

    @MockkBean
    private lateinit var jwtService: JwtService

    @MockkBean
    private lateinit var dbUserDetailsService: DBUserDetailsService

    @Test
    fun `should allow public access to GET products`() {
        every { productService.listProducts() } returns listOf()
        every { productService.listProductsByCategory(any()) } returns listOf()
        
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk)
    }

    @Test
    fun `should deny access to POST products for unauthenticated users`() {
        mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isForbidden)
    }

    @Test
    @WithMockUser(authorities = ["ROLE_CUSTOMER"])
    fun `should deny access to POST products for users with ROLE_CUSTOMER only`() {
        mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isForbidden)
    }

    @Test
    @WithMockUser(authorities = ["ROLE_ADMIN"])
    fun `should allow access to POST products for users with ROLE_ADMIN`() {
        val response = ProductResponse(
            id = "1", sku = 123, name = "Test", description = "Desc",
            price = 10.0, promoted = false, 
            category = CategoryResponse("c1", "Cat"),
            createdAt = LocalDateTime.now(), quantity = 10
        )
        every { productService.create(any()) } returns response
        
        mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "sku": 123,
                    "name": "Test Product",
                    "description": "Desc",
                    "price": 10.0,
                    "categoryId": "cat1"
                }
            """.trimIndent()))
            .andExpect(status().isCreated)
    }
}
