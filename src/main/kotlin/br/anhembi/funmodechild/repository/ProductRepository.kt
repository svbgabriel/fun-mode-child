package br.anhembi.funmodechild.repository

import br.anhembi.funmodechild.entity.Product
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : MongoRepository<Product, String> {
    fun findByPromotedIsTrue(): List<Product>

    fun findByCategoryId(categoryId: String): List<Product>
}
