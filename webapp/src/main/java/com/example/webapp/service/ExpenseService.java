package com.example.webapp.service;
/*  expense-parent
    14.06.2024
    @author DiachenkoDanylo
*/

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
@Service
public class ExpenseService {

    private final RestClient restClient;

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public ExpenseService(ClientRegistrationRepository clientRegistrationRepository,
                             OAuth2AuthorizedClientRepository authorizedClientRepository) {
        this.authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);

        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8081")
                .requestInterceptor((request, body, execution) -> {
                    if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                        var token = this.authorizedClientManager.authorize(
                                        OAuth2AuthorizeRequest.withClientRegistrationId("expense-app-client-credentials")
                                                .principal(SecurityContextHolder.getContext().getAuthentication())
                                                .build())
                                .getAccessToken().getTokenValue();

                        request.getHeaders().setBearerAuth(token);
                    }

                    return execution.execute(request, body);
                })
                .build();
    }
}
