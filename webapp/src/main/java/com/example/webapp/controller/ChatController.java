package com.example.webapp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*  expense-parent
    07.06.2024
    @author DiachenkoDanylo
*/
@RequestMapping("/chat/")
@Controller
public class ChatController {

    @GetMapping("")
    public String indexPage(Model model, @AuthenticationPrincipal OAuth2User oAuth2User){
        model.addAttribute("username", oAuth2User.getAttributes().get("email"));
        return "allChats";
    }

    @GetMapping("{id}")
    public String showChat(@PathVariable("id") String id, Model model, @AuthenticationPrincipal OAuth2User oAuth2User){
        model.addAttribute("ticketId",id);
        model.addAttribute("username", oAuth2User.getAttributes().get("email").toString());
        return "chatMain";
    }

}
