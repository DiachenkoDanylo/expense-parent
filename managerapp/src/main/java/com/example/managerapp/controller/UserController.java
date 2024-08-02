package com.example.managerapp.controller;
/*  expense-parent
    01.06.2024
    @author DiachenkoDanylo
*/

import com.example.managerapp.model.CategoryDTO;
import com.example.managerapp.model.ClientUserDTO;
import com.example.managerapp.service.manager.ExpenseService;
import com.example.managerapp.service.manager.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/users")
@Controller
@AllArgsConstructor
public class UserController {

    private final UsersService usersService;
    private final ExpenseService expenseService;

    @GetMapping("/")
    public String showAllUsers(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                               @RequestParam(value = "users_per_page", defaultValue = "10", required = false) Integer usersPerPage,
                               Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {
        List<ClientUserDTO> res = usersService.getAllUsers();
        if (page == null || usersPerPage == null) {
            model.addAttribute("usersList", usersService.getAllUsers());
        } else {
            model.addAttribute("usersList", usersService.getAllObjectsWithPagination(page, usersPerPage));
            model.addAllAttributes(usersService.pageAttributes(res,page,usersPerPage));
        }
        return "user/showAllUsers";
    }

    @GetMapping("/{id}")
    public String showCategoriesByUser(Model model, @PathVariable("id") Integer id) {
        CategoryDTO unassigned = new CategoryDTO(-1,"Unassigned","Unassigned expenses");
        List<CategoryDTO> list = new ArrayList<>(usersService.getUserCategoriesById(id));
        list.add(unassigned);
        model.addAttribute("categoryList", list);
        model.addAttribute("userId", id);
        return "user/showUser";
    }

    @GetMapping("/{id}/{catId}")
    public String showExpenses2(Model model, @PathVariable("id") Integer id, @PathVariable("catId") Integer catId) {
        if(catId == -1) {
            model.addAttribute("expenseList",expenseService.getAllExpensesByUserIdAndNullCategory(id));
        } else {
            model.addAttribute("expenseList",expenseService.getAllExpensesByUserIdAndCategoryId(catId, id));
        }
        model.addAttribute("userId",id);
        return "user/showCategories";
    }
}
