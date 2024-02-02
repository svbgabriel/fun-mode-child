package br.anhembi.funmodechild.repository

import br.anhembi.funmodechild.entity.Payment
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository : MongoRepository<Payment, String>
