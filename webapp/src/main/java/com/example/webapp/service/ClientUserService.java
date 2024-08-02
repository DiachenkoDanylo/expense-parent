package com.example.webapp.service;

import com.example.webapp.model.ClientUserDTO;
import com.example.webapp.model.ExpenseDTO;
import com.example.webapp.exception.CustomException;
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

/*  expense-parent
    11.06.2024
    @author DiachenkoDanylo
*/
@Service
public class ClientUserService {

    private final RestClient restClient;

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public ClientUserService(ClientRegistrationRepository clientRegistrationRepository,
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


    public List<ExpenseDTO> findByUserUsername(String email) {
        System.out.println(restClient.get().uri("/expense/"+email)
                .retrieve().body(String.class));
        ExpenseDTO[] result = restClient.get()
                .uri("/expense/{email}",email)
                .retrieve()
                .body(ExpenseDTO[].class);
        List<ExpenseDTO> res = Arrays.stream(result).toList();
        System.out.println();
        return res;
    }

    public List<ExpenseDTO> findWithPagination(int page, int expensePerPage,String email) {
        List<ExpenseDTO> res = getUsername(email);
        int k = page*expensePerPage;
        if(k>res.size())
            return res.subList(k-expensePerPage,res.size());
        return res.subList(k-expensePerPage,k);

    }

    public int getPages(int expensePerPage, String email) {
        List<ExpenseDTO> res = getUsername(email);
        return (int) Math.ceil(res.size()/expensePerPage);
    }

    public void createClienUser(String email) {
        restClient.post().uri("/user").body(new ClientUserDTO(email)).retrieve();
    }

    public List<ExpenseDTO> getUsername(String email) {
        try {
            return restClient.get()
                    .uri("/expense/{email}",email)
                    .accept(APPLICATION_JSON)
                    .exchange((request, response) -> {
                        if (response.getStatusCode().is4xxClientError()) {
                            throw new CustomException(response.bodyTo(CustomException.class));
                        } else {
                            return Arrays.stream(response.bodyTo(ExpenseDTO[].class)).toList();
                        }
                    });
        }catch (CustomException exception) {
            createClienUser(email);
            return null;
        }
    }


}
