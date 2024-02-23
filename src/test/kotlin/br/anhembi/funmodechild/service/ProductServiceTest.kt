package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.category
import br.anhembi.funmodechild.model.common.ProductNotFoundException
import br.anhembi.funmodechild.model.request.ProductRequest
import br.anhembi.funmodechild.product
import br.anhembi.funmodechild.repository.ProductRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class ProductServiceTest {

    @MockK
    private lateinit var productRepository: ProductRepository

    @MockK
    private lateinit var categoryService: CategoryService

    @InjectMockKs
    private lateinit var productService: ProductService

    @Test
    fun `should find product by id successfully`() {
        val id = product.id!!
        every { productRepository.findById(id) } returns Optional.of(product)

        val result = productService.findProductById(id)

        assertEquals(product.toApiResponse(), result)
    }

    @Test
    fun `should create product successfully`() {
        val request = ProductRequest(
            sku = 1,
            name = "New Product",
            description = "Desc",
            price = 10.0,
            categoryId = category.id!!
        )

        every { categoryService.findById(request.categoryId) } returns category
        every { productRepository.save(any()) } returns product

        val result = productService.create(request)

        verify { productRepository.save(any()) }
        assertEquals(product.toApiResponse(), result)
    }

    @Test
    fun `should update product successfully`() {
        val id = product.id!!
        val request = ProductRequest(
            sku = 1,
            name = "Updated Product",
            description = "Updated Desc",
            price = 15.0,
            categoryId = category.id!!
        )

        every { productRepository.findById(id) } returns Optional.of(product)
        every { productRepository.save(any()) } returns product

        val result = productService.update(id, request)

        verify { productRepository.save(any()) }
        assertEquals(product.toApiResponse(), result)
    }

    @Test
    fun `should delete product successfully`() {
        val id = product.id!!
        every { productRepository.existsById(id) } returns true
        every { productRepository.deleteById(id) } returns Unit

        productService.delete(id)

        verify { productRepository.deleteById(id) }
    }

    @Test
    fun `should throw exception when product not found for deletion`() {
        every { productRepository.existsById(any()) } returns false

        assertThrows<ProductNotFoundException> {
            productService.delete("invalid")
        }
    }
}
