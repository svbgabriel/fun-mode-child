package br.anhembi.funmodechild.model.request

import br.anhembi.funmodechild.entity.Customer
import br.anhembi.funmodechild.model.common.Address
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class UserRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:NotBlank(message = "Surname is required")
    val surname: String,

    @field:NotNull(message = "Birth date is required")
    @field:JsonFormat(pattern = "yyyy-MM-dd") @param:JsonFormat(pattern = "yyyy-MM-dd")
    val birthDate: LocalDate,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:Valid
    val address: Address? = null,

    @field:NotBlank(message = "Identification is required")
    val customerIdentification: String,

    @field:NotBlank(message = "Phone number is required")
    val phoneNumber: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters")
    val password: String
) {
    fun toDoc(encondePassword: String) = Customer(
        id = null,
        name = this.name,
        surname = this.surname,
        birthDate = this.birthDate,
        email = this.email,
        address = this.address ?: Address(),
        customerIdentification = this.customerIdentification,
        phoneNumber = this.phoneNumber,
        password = encondePassword,
        enabled = true
    )
}
