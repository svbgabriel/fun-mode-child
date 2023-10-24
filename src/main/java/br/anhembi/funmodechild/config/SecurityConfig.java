package br.anhembi.funmodechild.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String LOGIN_PAGE = "/login";

    @Bean
    public AuthenticationManager authenticationManager(BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserDetailsService userDetailService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
            authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(LOGIN_PAGE, "/registration", "/", "/produto/**", "/assets/**").permitAll()
                .anyRequest()
                .authenticated()
        );
        http.csrf(
            csrf -> csrf.disable()
        );
        http.formLogin(
            customizer -> customizer
                .loginPage(LOGIN_PAGE)
                .usernameParameter("email")
                .passwordParameter("senha")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
        ).logout(
            customizer -> customizer
                .logoutUrl("/logout")
                .logoutSuccessUrl(LOGIN_PAGE)
        );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
