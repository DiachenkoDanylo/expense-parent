package com.example.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/*  expense-parent
    25.05.2024
    @author DiachenkoDanylo
*/
@Configuration
@EnableWebSecurity
public class Config {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
               .authorizeHttpRequests(configurer -> configurer
                       .requestMatchers("/user").hasRole("USER")
                       .requestMatchers("/").permitAll()
                       .anyRequest().authenticated())
               .oauth2Login(configurer -> configurer.defaultSuccessUrl("/user"))
//               .oauth2ResourceServer(customizer -> customizer.jwt())
               .build();
    }
}
