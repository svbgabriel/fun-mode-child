package br.anhembi.funmodechild.util;

import br.anhembi.funmodechild.entity.Customer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

    private AuthenticationUtil() {}

    public static Customer getCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Customer) authentication.getPrincipal();
    }
}
