package br.anhembi.funmodechild.entity

import br.anhembi.funmodechild.model.common.Address
import br.anhembi.funmodechild.model.common.LoggedUser
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("customers")
data class Customer(
    @Id
    val id: String? = null,
    val name: String,
    val surname: String,
    val birthDate: LocalDate,
    val email: String,
    val address: Address? = null,
    val customerIdentification: String,
    val phoneNumber: String,
    var password: String,
    val enabled: Boolean = true,
    val accountNonExpired: Boolean = true,
    val credentialsNonExpired: Boolean = true,
    val accountNonLocked: Boolean = true
) {
    fun toLoggedUser() = LoggedUser(
        id = this.id!!,
        email = this.email,
        password = this.password,
        accountNonLocked = this.accountNonLocked,
        accountNonExpired = this.accountNonExpired,
        credentialsNonExpired = this.credentialsNonExpired,
        enabled = this.enabled
    )
}
