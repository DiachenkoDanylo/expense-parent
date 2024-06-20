package com.example.webapp.controller;
/*  expense-parent
    05.06.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.model.ExpenseDTO;
import com.example.webapp.exception.CustomException;
import com.example.webapp.model.ExpensePayload;
import com.example.webapp.service.ClientUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@RequestMapping("/expense")
@Controller
@AllArgsConstructor
public class ExpenseController {

     private final  ClientUserService clientUserService;

    @GetMapping("/")
    public String showExpense(Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {
        try {
            model.addAttribute("expenseList", clientUserService.getUsername(oAuth2User.getAttributes().get("email").toString()));
            return "expense/showExpenses";
        }catch (CustomException e) {
            if (Objects.equals(e.getMessage(), "Username " + oAuth2User.getAttributes().get("email") + " are NOT exists in our service")) {
                clientUserService.createClienUser(oAuth2User.getAttributes().get("email").toString());
                model.addAttribute("expenseList", clientUserService.getUsername(oAuth2User.getAttributes().get("email").toString()));
                return "expense/showExpenses";
            }else {
                return null;
            }
        }
    }
    @PostMapping("/")
    public String addNewExpense(@AuthenticationPrincipal OAuth2User oAuth2User,
                                @ModelAttribute("expense") ExpensePayload expensePayload,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> System.out.println(error.toString()));
            return null; }
        clientUserService.addExpense(oAuth2User,expensePayload);
        return "redirect:/expense/";
    }

    @GetMapping("/new")
    public String addExpenseForm(Model model, @AuthenticationPrincipal OAuth2User oAuth2User,
                                 @ModelAttribute("expense") ExpenseDTO expenseDTO) {

        //model.addAttribute("categories",dtoList);
        return "expense/newExpenseForm";
    }


}
