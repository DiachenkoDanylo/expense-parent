package com.example.managerapp.controller;
/*  expense-parent
    01.08.2024
    @author DiachenkoDanylo
*/

import com.example.managerapp.service.manager.HelpTicketService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ChatController {

    private final HelpTicketService helpTicketService;

    public ChatController(HelpTicketService helpTicketService) {
        this.helpTicketService = helpTicketService;
    }

    @GetMapping("")
    public String index(Model model, @AuthenticationPrincipal OAuth2User oAuth2User){
        return "index";
    }

    @GetMapping("chat/")
    public String allChats(Model model, @AuthenticationPrincipal OAuth2User oAuth2User){
        model.addAttribute("chatList", helpTicketService.getAllTickets());
        model.addAttribute("username",oAuth2User.getAttributes().get("email").toString());
        return "allChat";
    }

    @GetMapping("chat/{ticketId}")
    public String chat(Model model,
            @PathVariable(value = "ticketId") String ticketId, @AuthenticationPrincipal OAuth2User oAuth2User){
        model.addAttribute("messageList", helpTicketService.getMessages(ticketId));
        model.addAttribute("username",oAuth2User.getAttributes().get("email").toString());
        model.addAttribute("sendTo",helpTicketService.getTicketByTicketId(ticketId).get().getUser());
        model.addAttribute("ticketId",ticketId);
        return "chatMain";
    }

    @PostMapping("chat/{ticketId}/mark")
    public String markedAsSolvedOrUnsolved(Model model,
                                @PathVariable(value = "ticketId") String ticketId, @AuthenticationPrincipal OAuth2User oAuth2User){
        helpTicketService.markedAsSolvedUnsolved(helpTicketService.getTicketByTicketId(ticketId).get());
        return "redirect:/chat/{ticketId}";
    }
}
