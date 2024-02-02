package br.anhembi.funmodechild.controller

import br.anhembi.funmodechild.model.response.CategoryResponse
import br.anhembi.funmodechild.service.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/categories"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    @Operation(summary = "Get a list of available categories")
    fun listCategories(): List<CategoryResponse> = categoryService.listCategories()
}
