package com.example.webapp.controller;
/*  expense-parent
    01.06.2024
    @author DiachenkoDanylo
*/

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@RequestMapping("/user/")
@Controller
public class MainController {

    String logLinks = "/oauth2/authorization/expense-app-client-credentials";

//    private final RestTemplate restTemplate;
//
//    public MainController(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    @GetMapping
    public String indexUser(Model model) {
            String url = "http://172.17.0.1:6062/expense?id=1";
//            Object response = restTemplate.getForObject(url, Object.class);
//        model.addAttribute("expense",response);
        return "user/mainUser";
    }
}
