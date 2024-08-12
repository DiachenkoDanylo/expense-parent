package com.example.managerapp.service.manager;

import com.example.managerapp.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/*  expense-parent
    28.07.2024
    @author DiachenkoDanylo
*/
@Service
public class LogService extends PaginationService<String>{


    private final RestClient restClient;
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @Value("${value.custom.service-port}")
    private String servicePort;


    public LogService(ClientRegistrationRepository clientRegistrationRepository,
                          OAuth2AuthorizedClientRepository authorizedClientRepository) {
        this.authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);

        this.restClient = RestClient.builder()
                .baseUrl(servicePort)
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


    public List<String> getAllLogs() {
        return restClient.get()
                .uri("/audit/users/")
                .accept(APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        throw new CustomException(response.bodyTo(CustomException.class));
                    } else {
                        return Arrays.stream( response.bodyTo(String[].class)).toList();
                    }
                });
    }

    public List<String> getLogsByUserId(Integer userId) {
        return restClient.get()
                .uri("/audit/users/{userId}", userId)
                .accept(APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        throw new CustomException(response.bodyTo(CustomException.class));
                    } else {
                        return Arrays.stream( response.bodyTo(String[].class)).toList();
                    }
                });
    }



    @Override
    public List<String> getAllObjectsWithPagination(List<String> res,int page, int clientsPerPage) {
        return  paginationConvert(res, page, clientsPerPage);
    }

    @Override
    public List<String> paginationConvert(List<String> res, int page, int objectsPerPage) {
        if (res == null) {
            return null;
        }
        int k = page * objectsPerPage;
        if (k > res.size())
            return res.subList(k - objectsPerPage, res.size());
        return res.subList(k - objectsPerPage, k);
    }

    @Override
    public int getPages(List<String> res, int objectPerPage) {
        return (int) Math.ceil(res.size()/objectPerPage);
    }

    @Override
    public List<String> getAllObjectsWithPagination(int page, int clientsPerPage) {
        return List.of();
    }

    public Map<String,Integer> pageAttributes(List<String> res, int page, int objectsPerPage) {
        Map<String,Integer> attrib = new HashMap<>();
        attrib.put("page",page);
        attrib.put("size",objectsPerPage);
        attrib.put("totalPages",getPages(res,objectsPerPage));
        return attrib;
    }
}
