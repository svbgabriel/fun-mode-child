package br.anhembi.funmodechild.util

import br.anhembi.funmodechild.model.common.LoggedUser
import org.springframework.security.core.context.SecurityContextHolder

object AuthenticationUtil {

    @JvmStatic
    fun getLoggedUser(): LoggedUser {
        val authentication = SecurityContextHolder.getContext().authentication
        return authentication.principal as LoggedUser
    }
}
