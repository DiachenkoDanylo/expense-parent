package com.example.service.controller;

import com.example.service.dto.CategoryPayload;
import com.example.service.dto.ClientUserDTO;
import com.example.service.dto.ExpenseDTO;
import com.example.service.dto.manager.UserCategoryExpenseDTO;
import com.example.service.dto.manager.UsersDTO;
import com.example.service.entity.Category;
import com.example.service.service.CategoryService;
import com.example.service.service.ClientUserServiceImpl;
import com.example.service.service.ExpenseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*  expense-parent
    21.07.2024
    @author DiachenkoDanylo
*/
@RestController
@RequestMapping("/manager/")
@AllArgsConstructor
@Slf4j
public class ManagerController {

    private final CategoryService categoryService;
    private final ClientUserServiceImpl clientUserService;
    private final ExpenseService expenseService;

    @GetMapping("users")
    public List<UsersDTO> getUsers() {
        clientUserService.getAllUsers().forEach(p -> System.out.println(p.toString()));
        return clientUserService.getAllUsers();
    }


    @GetMapping("users/{id}")
    public UsersDTO getUserById(@PathVariable("id") Integer id) {
        return clientUserService.getUserDTOById(id);
    }

    @GetMapping("users/{id}/categories")
    public List<Category> getUserCategoryById(@PathVariable("id") Integer id) {
        return categoryService.getAllCategoriesByClient(clientUserService.getUsernameUserById(id));
    }

    @GetMapping("users/{id}/{category}")
    public List<ExpenseDTO> getExpensesByUserAndCategory(@PathVariable("id") Integer id,
                                                @PathVariable("category") Integer category) {
        System.out.println("user id = "+id + "  "+"category id = "+category);
        return expenseService.getAllExpensesDTOByClientCategory(clientUserService.getUserDTOById(id).getUsername(),category);
    }

}
