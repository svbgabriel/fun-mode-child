package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.model.response.CategoryResponse
import br.anhembi.funmodechild.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
    fun listCategories(): List<CategoryResponse> =
        categoryRepository.findAll()
            .map { it.toApiResponse() }
}
