package com.example.managerapp.controller;
/*  expense-parent
    01.08.2024
    @author DiachenkoDanylo
*/

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {


    @GetMapping("")
    public String index(Model model, @AuthenticationPrincipal OAuth2User oAuth2User){
        model.addAttribute("user",oAuth2User.getAttributes().get("email").toString());
        return "index";
    }
}
