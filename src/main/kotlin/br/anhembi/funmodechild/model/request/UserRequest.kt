package br.anhembi.funmodechild.model.request

import br.anhembi.funmodechild.entity.Customer
import br.anhembi.funmodechild.model.common.Address
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.Email
import java.time.LocalDate

data class UserRequest(
    val name: String,
    val surname: String,
    @field:JsonFormat(pattern = "yyyy-MM-dd") @param:JsonFormat(pattern = "yyyy-MM-dd") val birthDate: LocalDate,
    @field:Email val email: String,
    val address: Address,
    val customerIdentification: String,
    val phoneNumber: String,
    val password: String
) {
    fun toDoc(encondePassword: String) = Customer(
        id = null,
        name = this.name,
        surname = this.surname,
        birthDate = this.birthDate,
        email = this.email,
        address = this.address,
        customerIdentification = this.customerIdentification,
        phoneNumber = this.phoneNumber,
        password = encondePassword,
        enabled = true
    )
}
