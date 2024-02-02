package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.model.common.PasswordNotMatchException
import br.anhembi.funmodechild.model.common.UserNotFoundException
import br.anhembi.funmodechild.model.request.UserRequest
import br.anhembi.funmodechild.repository.CustomerRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    fun create(request: UserRequest) {
        val customer = request.toDoc(passwordEncoder.encode(request.password))
        customerRepository.save(customer)
    }

    fun updatePassword(username: String, oldPassword: String, newPassword: String, newPasswordConfirm: String) {
        val customer = customerRepository.findByEmail(username) ?: throw UserNotFoundException("User not found")

        // Checa se as novas senha são iguais
        if (newPassword != newPasswordConfirm) {
            throw PasswordNotMatchException("As senhas novas são diferentes")
        }

        // Checa se a senha antiga bate com a informada
        if (!passwordEncoder.matches(oldPassword, customer.password)) {
            throw PasswordNotMatchException("Senha antiga inválida")
        }

        // Altera a senha
        customer.password = passwordEncoder.encode(newPassword)
        customerRepository.save(customer)
    }
}
