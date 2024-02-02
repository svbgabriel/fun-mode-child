package br.anhembi.funmodechild.util;

import br.anhembi.funmodechild.model.common.LoggedUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

    private AuthenticationUtil() {}

    public static LoggedUser getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LoggedUser) authentication.getPrincipal();
    }
}
