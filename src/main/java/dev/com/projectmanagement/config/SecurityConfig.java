package dev.com.projectmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("removal")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
