package br.anhembi.funmodechild.repository

import br.anhembi.funmodechild.entity.Customer
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : MongoRepository<Customer, String> {
    fun findByEmail(email: String): Customer?
}
