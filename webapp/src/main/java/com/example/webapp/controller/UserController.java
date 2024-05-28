package com.example.webapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/*  expense-parent
    26.05.2024
    @author DiachenkoDanylo
*/
@Controller
public class UserController {

    @GetMapping("/user")
    public String  getUser(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {

        model.addAttribute("mapUser",oAuth2User.getAttributes());
        return "hello";
    }
}
