//package com.example.userserviceapp.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.web.SecurityFilterChain;
//
///*  expense-parent
//    26.05.2024
//    @author DiachenkoDanylo
//*/
//@Configuration
//@EnableWebSecurity
//public class SecurityBeans {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(configurer -> configurer.anyRequest().authenticated())
//                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .csrf(CsrfConfigurer::disable)
//                .oauth2Login(Customizer.withDefaults())
//                .oauth2ResourceServer(configurer -> configurer.jwt(jwt ->{
//                    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//                    jwtAuthenticationConverter.setPrincipalClaimName("preferred_username");
//                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter);
//                }))
//                .build();
//    }
//}
