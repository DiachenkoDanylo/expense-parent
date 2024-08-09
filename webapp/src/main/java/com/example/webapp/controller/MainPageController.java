package com.example.webapp.controller;

import com.example.webapp.model.MessageSentEvent;
import com.example.webapp.service.Consumer;
import com.example.webapp.service.Producer;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*  expense-parent
    07.06.2024
    @author DiachenkoDanylo
*/
@RequestMapping("/chat/")
@Controller
public class MainPageController {

    @GetMapping("")
    public String indexPage(Model model, @AuthenticationPrincipal OAuth2User oAuth2User){
        model.addAttribute("username", oAuth2User.getAttributes().get("email"));
        return "chatTest";
    }

    @GetMapping("{id}")
    public String showChat(@PathVariable("id") String id, Model model, @AuthenticationPrincipal OAuth2User oAuth2User){
        model.addAttribute("username", oAuth2User.getAttributes().get("email"));
        return "chatTest";
    }
}
