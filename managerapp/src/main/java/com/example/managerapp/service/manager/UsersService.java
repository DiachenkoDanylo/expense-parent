package com.example.managerapp.service.manager;

import com.example.managerapp.exception.CustomException;
import com.example.managerapp.model.CategoryDTO;
import com.example.managerapp.model.ClientUserDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/*  expense-parent
    21.07.2024
    @author DiachenkoDanylo
*/

@Service
public class UsersService extends PaginationService<ClientUserDTO>{

    private final RestClient restClient;
    private final OAuth2AuthorizedClientManager authorizedClientManager;


    public UsersService(String servicePort,ClientRegistrationRepository clientRegistrationRepository,
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


    public List<ClientUserDTO> getAllUsers() {
        return restClient.get()
                .uri("/manager/users")
                .accept(APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        throw new CustomException(response.bodyTo(CustomException.class));
                    } else {
                        return Arrays.stream( response.bodyTo(ClientUserDTO[].class)).toList();
                    }
                });

    }

    public ClientUserDTO getUserById(int id) {
        return restClient.get()
                .uri("/manager/users/{id}",id)
                .accept(APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        throw new CustomException(response.bodyTo(CustomException.class));
                    } else {
                        return response.bodyTo(ClientUserDTO.class);
                    }
                });
    }

    @Override
    public List<ClientUserDTO> getAllObjectsWithPagination(int page, int clientsPerPage) {
        List<ClientUserDTO> res = getAllUsers();
        return  paginationConvert(res, page, clientsPerPage);
    }

    @Override
    public List<ClientUserDTO> getAllObjectsWithPagination(List<ClientUserDTO> res, int page, int clientsPerPage) {
        return List.of();
    }

    public List<CategoryDTO> getUserCategoriesById(Integer id) {
        return restClient.get()
                .uri("/manager/users/{id}/categories",id)
                .accept(APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        throw new CustomException(response.bodyTo(CustomException.class));
                    } else {
                        return Arrays.stream(response.bodyTo(CategoryDTO[].class)).toList();
                    }
                });
    }

    @Override
    public List<ClientUserDTO> paginationConvert(List<ClientUserDTO> res, int page, int objectsPerPage) {
        if (res == null) {
            return null;
        }
        int k = page * objectsPerPage;
        if (k > res.size())
            return res.subList(k - objectsPerPage, res.size());
        return res.subList(k - objectsPerPage, k);
    }

    @Override
    public int getPages(List<ClientUserDTO> res, int objectPerPage) {
        return (int) Math.ceil(res.size()/objectPerPage);
    }

    @Override
    public Map<String,Integer> pageAttributes(List<ClientUserDTO> res, int page, int objectsPerPage) {
        Map<String,Integer> attrib = new HashMap<>();
        attrib.put("page",page);
        attrib.put("size",objectsPerPage);
        attrib.put("totalPages",getPages(res,objectsPerPage));
        return attrib;
    }

}
