package com.example.webapp.service;
/*  expense-parent
    14.06.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.model.CategoryDTO;
import com.example.webapp.exception.CustomException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class CategoryService {

    private final RestClient restClient;

    private final OAuth2AuthorizedClientManager authorizedClientManager;


    public CategoryService(ClientRegistrationRepository clientRegistrationRepository,
                             OAuth2AuthorizedClientRepository authorizedClientRepository) {
        this.authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);

        this.restClient = RestClient.builder()
//                .baseUrl("http://172.17.0.1:6062")
                .baseUrl("http://localhost:6062")
                .requestInterceptor((request, body, execution) -> {
                    if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                        var token = this.authorizedClientManager.authorize(
                                OAuth2AuthorizeRequest
                                        .withClientRegistrationId("expense-app-client-credentials")
                                        .principal(SecurityContextHolder.getContext().getAuthentication())
                                        .build())
                                .getAccessToken().getTokenValue();
                        request.getHeaders().setBearerAuth(token);
                    }
                    return execution.execute(request, body);
                })
                .build();
    }

    public List<CategoryDTO> getCategoriesByClientUsername(OAuth2User oAuth2User) {
        return restClient.get()
                .uri("/category/{email}",oAuth2User.getAttributes().get("email"))
                .accept(APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        throw new CustomException(response.bodyTo(CustomException.class));
                    } else {
                        return Arrays.stream(response.bodyTo(CategoryDTO[].class)).toList();
                    }
                });
    }

    public CategoryDTO getCategoryById(OAuth2User oAuth2User, int id) {
        if(id== -1){
            return new CategoryDTO("Unassigned","Unassigned expenses");
        }else {
            return restClient.get()
                    .uri("/category/{email}/{id}",oAuth2User.getAttributes().get("email"),id)
                    .accept(APPLICATION_JSON)
                    .exchange((request, response) -> {
                        if (response.getStatusCode().is4xxClientError()) {
                            throw new CustomException(response.bodyTo(CustomException.class));
                        } else {
                            return response.bodyTo(CategoryDTO.class);
                        }
                    });
        }
    }

    public void addNewCategory(OAuth2User oAuth2User,
                               CategoryDTO categoryDTO) {
        CategoryDTO categoryDTO1= new CategoryDTO(
                categoryDTO.getName(),
                categoryDTO.getDescription());
        restClient.post().uri(
                        "/category/{username}",
                        oAuth2User.getAttributes().get("email").toString())
                .body(categoryDTO1).retrieve();
    }

    public void updateCategory(OAuth2User oAuth2User,
                              CategoryDTO categoryDTO, int id) {
        CategoryDTO categoryDTO1= new CategoryDTO(
                id,
                categoryDTO.getName(),
                categoryDTO.getDescription());
        restClient.patch().uri(
                        "/category/{username}",
                        oAuth2User.getAttributes().get("email").toString())
                .body(categoryDTO1).retrieve();
    }

    public void deleteCategory(OAuth2User oAuth2User, Integer id, boolean include) {
        if (include) {
            deleteCategoryWithExpenses(oAuth2User,id);
        } else {
            deleteCategoryWithoutExpenses(oAuth2User, id);
        }
    }

    public void deleteCategoryWithExpenses(OAuth2User oAuth2User, Integer id) {
        restClient.delete().uri(
                        "/category/{username}?id={id}&with=true",
                        oAuth2User.getAttributes().get("email").toString(),id)
                .retrieve();
    }

    public void deleteCategoryWithoutExpenses(OAuth2User oAuth2User, Integer id) {
        restClient.delete().uri(
                        "/category/{username}?id={id}&with=false",
                        oAuth2User.getAttributes().get("email").toString(),id)
                .retrieve();
    }

}
