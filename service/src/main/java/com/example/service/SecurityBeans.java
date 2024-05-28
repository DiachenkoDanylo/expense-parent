//package com.example.service;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
///*  expense-parent
//    25.05.2024
//    @author DiachenkoDanylo
//*/
////@Configuration
////public class SecurityBeans {
////
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        return http
////                .authorizeHttpRequests(configurer -> configurer.anyRequest().authenticated())
////                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .csrf(CsrfConfigurer::disable)
////                .oauth2ResourceServer(configurer -> configurer.jwt(Customizer.withDefaults()))
////
////        .build();
////    }
////}
