package br.anhembi.funmodechild.controller

import br.anhembi.funmodechild.model.response.ProductResponse
import br.anhembi.funmodechild.service.ProductService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/products"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Tag(name = "Products")
class ProductController(private val productService: ProductService) {

    @GetMapping("/{productId}")
    fun findProductById(@PathVariable("productId") id: String): ProductResponse {
        return productService.findProductById(id)
    }

    @GetMapping("/promoted")
    fun listPromotedProducts(): List<ProductResponse> {
        return productService.listPromotedProducts()
    }

    @GetMapping
    fun listProducts(@RequestParam(value = "categoryId", required = false) categoryId: String?): List<ProductResponse> {
        if (categoryId != null) {
            return productService.listProductsByCategory(categoryId)
        }

        return productService.listProducts()
    }
}
