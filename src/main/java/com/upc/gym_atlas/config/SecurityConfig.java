package com.upc.gym_atlas.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitamos CSRF para poder usar Postman sin token
                .csrf(csrf -> csrf.disable())

                // Configuramos qué endpoints están permitidos sin autenticación
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()  // TODA la API libre por ahora
                        .anyRequest().permitAll()
                )

                // Opcional: deshabilitar formulario de login y basic auth
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
