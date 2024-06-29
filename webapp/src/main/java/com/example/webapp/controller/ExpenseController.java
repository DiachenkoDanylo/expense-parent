package com.example.webapp.controller;
/*  expense-parent
    05.06.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.exception.CustomException;
import com.example.webapp.model.ExpenseDTO;
import com.example.webapp.model.ExpensePayload;
import com.example.webapp.service.CategoryService;
import com.example.webapp.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/expense")
@Controller
@AllArgsConstructor
public class ExpenseController {

     private final  ExpenseService expenseService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String showExpenses(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                              @RequestParam(value = "expenses_per_page", defaultValue = "10", required = false) Integer expensesPerPage,
                              Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {
        if (page == null || expensesPerPage == null) {
            model.addAttribute("expenseList", expenseService.getExpensesByUsername(oAuth2User.getAttributes().get("email").toString()));
        }else {
            model.addAttribute("expenseList", expenseService.findWithPagination(page, expensesPerPage,oAuth2User.getAttributes().get("email").toString()));
            model.addAttribute("page", page);
            model.addAttribute("size", expensesPerPage);
            model.addAttribute("totalPages", expenseService.getPages(expensesPerPage,oAuth2User.getAttributes().get("email").toString()));
        }
        return "expense/showAllExpenses";
    }

    @PostMapping("/")
    public String addNewExpense(@AuthenticationPrincipal OAuth2User oAuth2User,
                                @ModelAttribute("expense") ExpensePayload expensePayload,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> System.out.println(error.toString()));
            return null;
        }
        expenseService.addExpense(oAuth2User,expensePayload);
        return "redirect:/expense/";
    }

    @GetMapping("/edit")
    public String editExpense(Model model,@RequestParam(name = "id") int id, @AuthenticationPrincipal OAuth2User oAuth2User,
                              @ModelAttribute("expense") ExpenseDTO expenseDTO) {
        try {
            model.addAttribute("expense",expenseService.getExpenseByUsernameAndId(id,oAuth2User));
            model.addAttribute("categories",categoryService.getCategoriesByClientUsername(oAuth2User));
        }catch (CustomException e) {
            return "redirect:/expense/";
        }
        return "expense/editExpense";
    }

    @PostMapping("/update")
    public String updateExpense(@AuthenticationPrincipal OAuth2User oAuth2User,
                                @ModelAttribute("expense") ExpensePayload expensePayload,
                                @RequestParam("id") int id,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> System.out.println(error.toString()));
            return null;
        }
        expenseService.updateExpense(oAuth2User,expensePayload,id);
        return "redirect:/expense/";
    }

    @GetMapping("/{id}")
    public String showExpense(Model model,@PathVariable(name = "id")int id, @AuthenticationPrincipal OAuth2User oAuth2User) {
//        model.addAttribute("expense",expenseService.)
        return null;
    }

    @GetMapping("/new")
    public String addExpenseForm(Model model, @AuthenticationPrincipal OAuth2User oAuth2User,
                                 @ModelAttribute("expense") ExpenseDTO expenseDTO ) {
        model.addAttribute("categories",categoryService.getCategoriesByClientUsername(oAuth2User));
        return "expense/newExpenseForm";
    }

    @PostMapping("/delete")
    public String deleteExpense(Model model, @AuthenticationPrincipal OAuth2User oAuth2User,
                                @RequestParam("id") Integer id) {
        System.out.println("Id of deleting card is "+id);

        //model.addAttribute("categories",dtoList);
        return "redirect:/expense/";
//                "expense/newExpenseForm";
    }


}
