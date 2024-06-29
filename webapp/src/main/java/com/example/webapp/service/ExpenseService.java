package com.example.webapp.service;
/*  expense-parent
    14.06.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.exception.CustomException;
import com.example.webapp.model.ClientUserDTO;
import com.example.webapp.model.ExpenseDTO;
import com.example.webapp.model.ExpensePayload;
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
public class ExpenseService {

    private final RestClient restClient;

    private final OAuth2AuthorizedClientManager authorizedClientManager;
    private final CategoryService categoryService;

    public ExpenseService(ClientRegistrationRepository clientRegistrationRepository,
                          OAuth2AuthorizedClientRepository authorizedClientRepository, CategoryService categoryService) {
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
        this.categoryService = categoryService;
    }




    public List<ExpenseDTO> getAllByCategoryAndUser(String email,int id) {
        System.out.println(restClient.get().uri("/expense/"+email+"/"+id)
                .retrieve().body(String.class));
        ExpenseDTO[] result = restClient.get()
                .uri("/expense/{email}",email)
                .retrieve()
                .body(ExpenseDTO[].class);
        List<ExpenseDTO> res = Arrays.stream(result).toList();
        return res;
    }

    public List<ExpenseDTO> findWithPagination(int page, int expensePerPage,String email) {
        List<ExpenseDTO> res = getExpensesByUsername(email);
        int k = page*expensePerPage;
        if(k>res.size())
            return res.subList(k-expensePerPage,res.size());
        return res.subList(k-expensePerPage,k);

    }

    public int getPages(int expensePerPage, String email) {
        List<ExpenseDTO> res = getExpensesByUsername(email);
        return (int) Math.ceil(res.size()/expensePerPage);
    }

    public void createClienUser(String email) {
        restClient.post().uri("/user").body(new ClientUserDTO(email)).retrieve();
    }

    public List<ExpenseDTO> getExpensesByUsername(String email) {
        try {
            return restClient.get()
                    .uri("/expense/{email}",email)
                    .accept(APPLICATION_JSON)
                    .exchange((request, response) -> {
                        if (response.getStatusCode().is4xxClientError()) {
                            System.out.println(response.getStatusCode().toString());
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

    public ExpenseDTO getExpenseByUsernameAndId(int id,OAuth2User oAuth2User) {
        return restClient.get()
                .uri("/expense/{username}/?id={id}",oAuth2User.getAttributes().get("email").toString(),id)
                .accept(APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        throw new CustomException(response.bodyTo(CustomException.class));
                    } else {
                        return response.bodyTo(ExpenseDTO.class);
                    }
                });

    };

    public List<ExpenseDTO> getExpensesByUsernameAndCategory(int id,OAuth2User oAuth2User) {
        return restClient.get()
                .uri("/expense/{username}/category/{categoryId}",
                        oAuth2User.getAttributes().get("email").toString(),id)
                .accept(APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is4xxClientError()) {
                        throw new CustomException(response.bodyTo(CustomException.class));
                    } else {
                        return Arrays.stream( response.bodyTo(ExpenseDTO[].class)).toList();
                    }
                });

    };



    public void addExpense(OAuth2User oAuth2User,
                           ExpensePayload expensePayload) {
        ExpenseDTO expenseDTO= new ExpenseDTO(
                expensePayload.getAmount(),
                expensePayload.getDescription(),
                categoryService.getCategoryById(oAuth2User,expensePayload.getCategory()));

        restClient.post().uri(
                        "/expense/{username}",
                        oAuth2User.getAttributes().get("email").toString())
                .body(expenseDTO).retrieve();
    }

    public void updateExpense(OAuth2User oAuth2User,
                           ExpensePayload expensePayload,int id) {
        ExpenseDTO expenseDTO;
        if(expensePayload.getCategory()==0){
             expenseDTO= new ExpenseDTO(
                    id,
                    expensePayload.getAmount(),
                    expensePayload.getDescription(),
                    null);
        } else {
             expenseDTO= new ExpenseDTO(
                    id,
                    expensePayload.getAmount(),
                    expensePayload.getDescription(),
                    categoryService.getCategoryById(oAuth2User,expensePayload.getCategory()));
        }
        System.out.println("inside updateExpense \n \n \n "+expenseDTO.toString()+ "\n \n \n ");
//        restClient.patch().uri(
//                        "/expense/{username}",
//                        oAuth2User.getAttributes().get("email").toString())
//                .body(expenseDTO).retrieve();

        String uri = String.format("/expense/%s?id=%d",
                oAuth2User.getAttributes().get("email").toString(), id);

        restClient.patch()
                .uri(uri)
                .body(expenseDTO)  // Use bodyValue to set the request body
                .retrieve();
    }
}
