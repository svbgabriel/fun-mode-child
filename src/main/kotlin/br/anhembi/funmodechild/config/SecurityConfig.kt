package br.anhembi.funmodechild.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(private val jwtAuthFilter: JwtAuthFilter) {
    @Bean
    fun authenticationManager(
        bCryptPasswordEncoder: BCryptPasswordEncoder?,
        userDetailService: UserDetailsService?
    ): AuthenticationManager {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailService)
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder)

        return ProviderManager(authenticationProvider)
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/users/login",
                    "/users", // Public registration
                    "/assets/**",
                    "/api-docs/**",
                    "/swagger-ui/**"
                ).permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/categories/**", "/products/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/categories/**", "/products/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(org.springframework.http.HttpMethod.PUT, "/categories/**", "/products/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/categories/**", "/products/**").hasAuthority("ROLE_ADMIN")
                .anyRequest()
                .authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
