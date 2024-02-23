package br.anhembi.funmodechild.repository

import br.anhembi.funmodechild.entity.Cart
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : MongoRepository<Cart, String> {
    fun findByCustomerId(customerId: String): Cart?
    fun deleteByCustomerId(customerId: String)
}
