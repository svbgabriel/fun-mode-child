package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.category
import br.anhembi.funmodechild.model.request.CategoryRequest
import br.anhembi.funmodechild.repository.CategoryRepository
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
class CategoryServiceTest {

    @MockK
    private lateinit var categoryRepository: CategoryRepository

    @InjectMockKs
    private lateinit var categoryService: CategoryService

    @Test
    fun `should list categories successfully`() {
        every { categoryRepository.findAll() } returns listOf(category)

        val result = categoryService.listCategories()

        assertEquals(1, result.size)
        assertEquals(category.name, result[0].name)
    }

    @Test
    fun `should create category successfully`() {
        val request = CategoryRequest(name = "New Category")
        every { categoryRepository.save(any()) } returns category

        val result = categoryService.create(request)

        verify { categoryRepository.save(any()) }
        assertEquals(category.toApiResponse(), result)
    }

    @Test
    fun `should update category successfully`() {
        val id = category.id!!
        val request = CategoryRequest(name = "Updated Category")
        
        every { categoryRepository.findById(id) } returns Optional.of(category)
        every { categoryRepository.save(any()) } returns category

        val result = categoryService.update(id, request)

        verify { categoryRepository.save(any()) }
        assertEquals(category.toApiResponse(), result)
    }

    @Test
    fun `should delete category successfully`() {
        val id = category.id!!
        every { categoryRepository.findById(id) } returns Optional.of(category)
        every { categoryRepository.delete(any()) } returns Unit

        categoryService.delete(id)

        verify { categoryRepository.delete(any()) }
    }

    @Test
    fun `should throw exception when category not found for deletion`() {
        every { categoryRepository.findById(any()) } returns Optional.empty()

        assertThrows<NoSuchElementException> {
            categoryService.delete("invalid")
        }
    }
}
