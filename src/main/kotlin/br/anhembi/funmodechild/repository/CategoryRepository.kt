package br.anhembi.funmodechild.repository

import br.anhembi.funmodechild.entity.Category
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : MongoRepository<Category, String>
