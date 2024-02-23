package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.entity.Category
import br.anhembi.funmodechild.model.common.OrderNotFoundException
import br.anhembi.funmodechild.model.request.CategoryRequest
import br.anhembi.funmodechild.model.response.CategoryResponse
import br.anhembi.funmodechild.repository.CategoryRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun listCategories(): List<CategoryResponse> =
        categoryRepository.findAll()
            .map { it.toApiResponse() }

    fun findById(id: String): Category = 
        categoryRepository.findById(id).orElseThrow { 
            logger.warn("Category $id not found")
            NoSuchElementException("Category not found") 
        }

    fun create(request: CategoryRequest): CategoryResponse {
        logger.info("Creating new category: ${request.name}")
        val category = Category(name = request.name)
        val saved = categoryRepository.save(category)
        return saved.toApiResponse()
    }

    fun update(id: String, request: CategoryRequest): CategoryResponse {
        logger.info("Updating category $id")
        val category = findById(id)
        val updatedCategory = category.copy(name = request.name)
        val saved = categoryRepository.save(updatedCategory)
        return saved.toApiResponse()
    }

    fun delete(id: String) {
        logger.info("Deleting category $id")
        val category = findById(id)
        categoryRepository.delete(category)
    }
}
