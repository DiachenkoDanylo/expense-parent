package com.example.service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Stream;

/*  expense-parent
    07.06.2024
    @author DiachenkoDanylo
*/

@Configuration
public class SecurityConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/**").hasAnyRole("CUSTOMER","MANAGER")
                                .requestMatchers("/manager**").hasAnyRole("MANAGER","ADMIN")
                                .requestMatchers("/audit/**").hasAnyRole("MANAGER","ADMIN")
                                .anyRequest().authenticated())
//                                .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer
                                .jwt(jwt -> {
                                    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
                                    jwtAuthenticationConverter.setPrincipalClaimName("email");
                                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter);

                                    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

                                    JwtGrantedAuthoritiesConverter customJwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                                    customJwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("groups");
                                    customJwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

                                    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(token ->
                                        Stream.concat(jwtGrantedAuthoritiesConverter.convert(token).stream(),
                                                customJwtGrantedAuthoritiesConverter.convert(token).stream())
                                                .toList());
                                }))
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
