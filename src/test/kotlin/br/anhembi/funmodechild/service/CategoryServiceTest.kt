package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.entity.Category
import br.anhembi.funmodechild.repository.CategoryRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.bson.BsonObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CategoryServiceTest {

    @RelaxedMockK
    private lateinit var categoryRepository: CategoryRepository

    @InjectMockKs
    private lateinit var categoryService: CategoryService

    @Test
    fun `should list all categories`() {
        val category = Category(
            id = BsonObjectId().toString(),
            name = "Category Name"
        )

        every { categoryRepository.findAll() } returns listOf(category)

        val result = categoryService.listCategories()

        assertEquals(1, result.size)
    }
}
