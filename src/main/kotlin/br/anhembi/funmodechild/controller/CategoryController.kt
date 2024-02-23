package br.anhembi.funmodechild.controller

import br.anhembi.funmodechild.model.request.CategoryRequest
import br.anhembi.funmodechild.model.response.CategoryResponse
import br.anhembi.funmodechild.service.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/categories"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Categories", description = "Endpoints for managing product categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    @Operation(summary = "Get a list of all categories")
    fun listCategories(): List<CategoryResponse> = categoryService.listCategories()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new category (Admin only)")
    @ApiResponse(responseCode = "201", description = "Category created successfully")
    fun createCategory(@Valid @RequestBody request: CategoryRequest): CategoryResponse {
        return categoryService.create(request)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing category (Admin only)")
    @ApiResponse(responseCode = "200", description = "Category updated successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    fun updateCategory(
        @PathVariable id: String,
        @Valid @RequestBody request: CategoryRequest
    ): CategoryResponse {
        return categoryService.update(id, request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a category (Admin only)")
    @ApiResponse(responseCode = "204", description = "Category deleted successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    fun deleteCategory(@PathVariable id: String) {
        categoryService.delete(id)
    }
}
