package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.category
import br.anhembi.funmodechild.model.response.CategoryResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CategoryTest {

    @Test
    fun `should create a CategoryResponse from Category`() {
        val expected = CategoryResponse(
            id = category.id!!,
            name = "Category Name"
        )

        assertEquals(category.toApiResponse(), expected)
    }
}
