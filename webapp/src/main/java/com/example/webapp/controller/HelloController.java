//package com.example.webapp.controller;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestClient;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import java.security.Principal;
//
///*  expense-parent
//    25.05.2024
//    @author DiachenkoDanylo
//*/
//@Controller
//@RequestMapping("/api/")
//public class HelloController {
//
//    private final RestClient restService;
//    private final OAuth2AuthorizedClientManager authorizedClientManager;
//
//    public HelloController(RestClient restService, OAuth2AuthorizedClientManager authorizedClientManager, OAuth2AuthorizedClientManager authorizedClientManager1) {
//        this.authorizedClientManager = authorizedClientManager1;
//        this.restService = RestClient.builder()
//                .baseUrl("http:172.17.0.1:6062/")
//                .requestInterceptor((request, body, execution) -> {
//                    if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                        var token = this.authorizedClientManager.authorize(
//                                OAuth2AuthorizeRequest.withClientRegistrationId("greetings-app-client-credentials")
//                                .principal("greeting-app")
//                                        .build())
//                                .getAccessToken().getTokenValue();
//                        request.getHeaders().setBearerAuth(token);
//                    }
//                    return execution.execute(request,body);
//                })
//                .build();
//    }
//
//    @GetMapping("hi")
//    public String getHello(Model model, Principal principal) {
//        model.addAttribute("greetings",
//                this.restService.get().uri("api")
//                .retrieve().body(Hello.class));
//        return "hello";
//    }
//}
