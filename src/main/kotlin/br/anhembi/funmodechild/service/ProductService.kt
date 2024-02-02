package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.model.common.ProductNotFoundException
import br.anhembi.funmodechild.model.response.ProductResponse
import br.anhembi.funmodechild.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun listProductsByCategory(categoryId: String): List<ProductResponse> =
        productRepository.findByCategoryId(categoryId).map { it.toApiResponse() }

    fun listPromotedProducts(): List<ProductResponse> =
        productRepository.findByPromotedIsTrue().map { it.toApiResponse() }

    fun findProductById(id: String): ProductResponse =
        productRepository
            .findById(id)
            .orElseThrow { throw ProductNotFoundException("Product id $id not found") }
            .toApiResponse()

    fun listProducts(): List<ProductResponse> = productRepository.findAll().map { it.toApiResponse() }
}
