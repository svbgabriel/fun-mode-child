package br.anhembi.funmodechild.util

import br.anhembi.funmodechild.model.common.LoggedUser
import org.springframework.security.core.context.SecurityContextHolder

object AuthenticationUtil {

    fun getLoggedUser(): LoggedUser = (SecurityContextHolder.getContext().authentication.principal) as LoggedUser
}
