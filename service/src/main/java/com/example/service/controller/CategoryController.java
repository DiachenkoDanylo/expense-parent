package com.example.service.controller;

import com.example.service.entity.Category;
import com.example.service.exception.NotFoundException;
import com.example.service.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/
@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public List<Category> getAll() {

        return categoryService.getAllCategories();

    }

    @GetMapping("/{catId}")
    public  Category getCategoryById(@PathVariable ("catId") int catId , Principal principal) {
        System.out.println(principal.getName());
        return categoryService.getCategoryById(catId);
    }
}
