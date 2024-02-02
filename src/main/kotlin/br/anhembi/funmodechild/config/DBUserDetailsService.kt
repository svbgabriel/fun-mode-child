package br.anhembi.funmodechild.config

import br.anhembi.funmodechild.repository.CustomerRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class DBUserDetailsService(private val customerRepository: CustomerRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user = customerRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("No user found with username: $email")

        return user.toLoggedUser()
    }
}
