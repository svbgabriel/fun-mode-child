package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.entity.Product
import br.anhembi.funmodechild.model.common.ProductNotFoundException
import br.anhembi.funmodechild.model.request.ProductRequest
import br.anhembi.funmodechild.model.response.ProductResponse
import br.anhembi.funmodechild.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val categoryService: CategoryService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun listProductsByCategory(categoryId: String): List<ProductResponse> =
        productRepository.findByCategoryId(categoryId).map { it.toApiResponse() }

    fun listPromotedProducts(): List<ProductResponse> =
        productRepository.findByPromotedIsTrue().map { it.toApiResponse() }

    fun findProductById(id: String): ProductResponse =
        productRepository
            .findById(id)
            .orElseThrow {
                logger.warn("Product id $id not found")
                ProductNotFoundException("Product id $id not found")
            }
            .toApiResponse()

    fun listProducts(): List<ProductResponse> = productRepository.findAll().map { it.toApiResponse() }

    fun create(request: ProductRequest): ProductResponse {
        logger.info("Creating new product: ${request.name}")
        val category = categoryService.findById(request.categoryId)

        val product = Product(
            sku = request.sku,
            name = request.name,
            description = request.description,
            price = request.price,
            reference = request.reference,
            referenceBig = request.referenceBig,
            quantity = request.quantity,
            promoted = request.promoted,
            createdAt = LocalDateTime.now(),
            category = category
        )

        val saved = productRepository.save(product)
        return saved.toApiResponse()
    }

    fun update(id: String, request: ProductRequest): ProductResponse {
        logger.info("Updating product $id")
        val existing = productRepository.findById(id).orElseThrow {
            logger.warn("Product $id not found for update")
            ProductNotFoundException("Product not found")
        }

        val category = if (request.categoryId != existing.category.id) {
            categoryService.findById(request.categoryId)
        } else {
            existing.category
        }

        val updated = existing.copy(
            sku = request.sku,
            name = request.name,
            description = request.description,
            price = request.price,
            reference = request.reference,
            referenceBig = request.referenceBig,
            quantity = request.quantity,
            promoted = request.promoted,
            category = category
        )

        val saved = productRepository.save(updated)
        return saved.toApiResponse()
    }

    fun delete(id: String) {
        logger.info("Deleting product $id")
        if (!productRepository.existsById(id)) {
            logger.warn("Product $id not found for deletion")
            throw ProductNotFoundException("Product not found")
        }
        productRepository.deleteById(id)
    }
}
