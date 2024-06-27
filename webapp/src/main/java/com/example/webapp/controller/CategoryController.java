package com.example.webapp.controller;

import com.example.webapp.service.CategoryService;
import com.example.webapp.service.ClientUserService;
import com.example.webapp.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*  expense-parent
    14.06.2024
    @author DiachenkoDanylo
*/
@RequestMapping("/category")
@AllArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final ClientUserService clientUserService;
    private final ExpenseService expenseService;

    @GetMapping("/")
    public String showAllCategoryPage(Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {

        model.addAttribute("categoryList", categoryService.getCategoriesByClientUsername(oAuth2User));
        categoryService.getCategoriesByClientUsername(oAuth2User).stream().forEach(categoryDTO -> System.out.println(categoryDTO.toString()));
        return "category/showAllCategories";
    }

    @GetMapping("/{id}")
    public String showCategoryPage(@PathVariable ("id") int id, Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {
        clientUserService.getUsername(oAuth2User.getAttributes().get("email").toString()).stream();

        model.addAttribute("category", categoryService.getCategoryById(oAuth2User, id));
        System.out.println(expenseService.getAllByCategoryAndUser(oAuth2User.getAttributes().get("email").toString(),id));
        model.addAttribute("categoryExpenseList", clientUserService.getUsername(oAuth2User.getAttributes().get("email").toString()).stream());
        return "category/showCategory";
    }

}
