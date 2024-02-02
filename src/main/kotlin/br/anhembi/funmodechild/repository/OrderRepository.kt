package br.anhembi.funmodechild.repository

import br.anhembi.funmodechild.entity.Order
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : MongoRepository<Order, String> {
    fun findByCustomerId(customerId: String): List<Order>

    fun findByIdAndCustomerId(id: String, customerId: String): Order?
}
