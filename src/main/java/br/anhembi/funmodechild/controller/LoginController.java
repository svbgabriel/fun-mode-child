package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.model.request.AuthRequest;
import br.anhembi.funmodechild.model.response.AuthResponse;
import br.anhembi.funmodechild.service.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/users/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Tag(name = "Users")
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
            return new AuthResponse(jwtService.generateToken(authRequest.username()));
        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Login error: " + e.getMessage());
        }
    }
}
