package com.example.webapp.config;
/*  expense-parent
    13.08.2024
    @author DiachenkoDanylo
*/

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfiguration {


    @Value("${value.custom.service-port}")
    private  String  servicePort;

    // you set in application.yml
    @Bean
    public String getServicePort() {
        return servicePort;
    }
}