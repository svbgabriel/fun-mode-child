package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.category
import br.anhembi.funmodechild.repository.CategoryRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CategoryServiceTest {

    @RelaxedMockK
    private lateinit var categoryRepository: CategoryRepository

    @InjectMockKs
    private lateinit var categoryService: CategoryService

    @Test
    fun `should list all categories`() {
        every { categoryRepository.findAll() } returns listOf(category)

        val result = categoryService.listCategories()

        assertEquals(1, result.size)
    }
}
