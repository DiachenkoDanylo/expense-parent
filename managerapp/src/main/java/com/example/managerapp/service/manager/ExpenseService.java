package com.example.managerapp.service.manager;
/*  expense-parent
    14.06.2024
    @author DiachenkoDanylo
*/

import com.example.managerapp.exception.CustomException;
import com.example.managerapp.model.ExpenseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class ExpenseService {

    private final RestClient restClient;

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public ExpenseService(ClientRegistrationRepository clientRegistrationRepository,
                          OAuth2AuthorizedClientRepository authorizedClientRepository) {
        this.authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);

        this.restClient = RestClient.builder()

                .baseUrl("http://localhost:6062")
//                .baseUrl("http://172.17.0.1:6062")
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

    public List<ExpenseDTO> getAllExpensesByUserIdAndCategoryId(Integer category, Integer id) {
        return restClient.get()
                .uri("/manager/users/{id}/{category}",id,category)
                .accept(APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        throw new CustomException(response.bodyTo(CustomException.class));
                    } else {
                        return Arrays.stream( response.bodyTo(ExpenseDTO[].class)).toList();
                    }
                });

    }

    public Object getAllExpensesByUserIdAndNullCategory(Integer id) {
        return restClient.get()
                .uri("/manager/users/{id}/-1",id)
                .accept(APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        throw new CustomException(response.bodyTo(CustomException.class));
                    } else {
                        return Arrays.stream( response.bodyTo(ExpenseDTO[].class)).toList();
                    }
                });

    }
}
