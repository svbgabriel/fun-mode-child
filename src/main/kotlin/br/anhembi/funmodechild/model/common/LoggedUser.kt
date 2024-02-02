package br.anhembi.funmodechild.model.common

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class LoggedUser(
    val id: String,
    val email: String,
    @JvmField val password: String,
    val enabled: Boolean,
    val accountNonExpired: Boolean,
    val credentialsNonExpired: Boolean,
    val accountNonLocked: Boolean
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf()
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isAccountNonExpired(): Boolean {
        return this.accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return this.accountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return this.credentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return this.enabled
    }
}
