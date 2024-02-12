package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.model.response.CategoryResponse
import org.bson.BsonObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class CategoryTest {

    @Test
    fun `should create a CategoryResponse from Category`() {
        val id = BsonObjectId().toString()
        val category = Category(
            id = id,
            name = "Category Name"
        )

        val expected = CategoryResponse(
            id = id,
            name = "Category Name"
        )

        assertEquals(category.toApiResponse(), expected)
    }
}
