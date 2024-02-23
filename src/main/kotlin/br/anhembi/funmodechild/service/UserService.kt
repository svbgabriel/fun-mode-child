package br.anhembi.funmodechild.service

import br.anhembi.funmodechild.model.common.PasswordNotMatchException
import br.anhembi.funmodechild.model.common.UserNotFoundException
import br.anhembi.funmodechild.model.request.UserRequest
import br.anhembi.funmodechild.repository.CustomerRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun create(request: UserRequest) {
        logger.info("Registering new user with email: ${request.email}")
        val customer = request.toDoc(passwordEncoder.encode(request.password))
        customerRepository.save(customer)
        logger.info("User ${request.email} registered successfully")
    }

    fun updatePassword(username: String, oldPassword: String, newPassword: String, newPasswordConfirm: String) {
        logger.info("Password update request for user: $username")
        val customer = customerRepository.findByEmail(username) ?: throw UserNotFoundException("User not found").also {
            logger.warn("User not found for password update: $username")
        }

        // Checa se as novas senha são iguais
        if (newPassword != newPasswordConfirm) {
            logger.warn("Password update failed: New passwords do not match for user $username")
            throw PasswordNotMatchException("As senhas novas são diferentes")
        }

        // Checa se a senha antiga bate com a informada
        if (!passwordEncoder.matches(oldPassword, customer.password)) {
            logger.warn("Password update failed: Old password mismatch for user $username")
            throw PasswordNotMatchException("Senha antiga inválida")
        }

        // Altera a senha
        customer.password = passwordEncoder.encode(newPassword)
        customerRepository.save(customer)
        logger.info("Password updated successfully for user $username")
    }
}
