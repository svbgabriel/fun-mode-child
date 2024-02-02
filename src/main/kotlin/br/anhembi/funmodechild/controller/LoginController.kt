package br.anhembi.funmodechild.controller

import br.anhembi.funmodechild.model.request.AuthRequest
import br.anhembi.funmodechild.model.response.AuthResponse
import br.anhembi.funmodechild.service.JwtService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(private val jwtService: JwtService, private val authenticationManager: AuthenticationManager) {

    @PostMapping(value = ["/users/login"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Tag(name = "Users")
    fun authenticateAndGetToken(@RequestBody authRequest: AuthRequest): AuthResponse {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authRequest.username,
                    authRequest.password
                )
            )
            return AuthResponse(jwtService.generateToken(authRequest.username))
        } catch (e: AuthenticationException) {
            throw UsernameNotFoundException("Login error: " + e.message)
        }
    }
}
