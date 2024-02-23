package br.anhembi.funmodechild.controller

import br.anhembi.funmodechild.model.request.ProductRequest
import br.anhembi.funmodechild.model.response.ProductResponse
import br.anhembi.funmodechild.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/products"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Products", description = "Endpoints for viewing and managing products")
class ProductController(private val productService: ProductService) {

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by ID")
    @ApiResponse(responseCode = "200", description = "Product found")
    @ApiResponse(responseCode = "404", description = "Product not found")
    fun findProductById(@PathVariable("productId") id: String): ProductResponse {
        return productService.findProductById(id)
    }

    @GetMapping("/promoted")
    @Operation(summary = "List all promoted products")
    fun listPromotedProducts(): List<ProductResponse> {
        return productService.listPromotedProducts()
    }

    @GetMapping
    @Operation(summary = "List products (optional filter by category)")
    fun listProducts(@RequestParam(value = "categoryId", required = false) categoryId: String?): List<ProductResponse> {
        if (categoryId != null) {
            return productService.listProductsByCategory(categoryId)
        }

        return productService.listProducts()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new product (Admin only)")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    fun createProduct(@Valid @RequestBody request: ProductRequest): ProductResponse {
        return productService.create(request)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product (Admin only)")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    fun updateProduct(
        @PathVariable id: String,
        @Valid @RequestBody request: ProductRequest
    ): ProductResponse {
        return productService.update(id, request)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a product (Admin only)")
    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    fun deleteProduct(@PathVariable id: String) {
        productService.delete(id)
    }
}
