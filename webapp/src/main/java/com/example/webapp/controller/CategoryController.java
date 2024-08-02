package com.example.webapp.controller;

import com.example.webapp.exception.CustomException;
import com.example.webapp.model.CategoryDTO;
import com.example.webapp.service.CategoryService;
import com.example.webapp.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*  expense-parent
    14.06.2024
    @author DiachenkoDanylo
*/
@RequestMapping("/category")
@AllArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final ExpenseService expenseService;

    @GetMapping("/")
    public String showAllCategoryPage(Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {
        CategoryDTO unassigned = new CategoryDTO(-1,"Unassigned","Unassigned expenses");
        List<CategoryDTO> list = new ArrayList<>(categoryService.getCategoriesByClientUsername(oAuth2User));
        list.add(unassigned);
        model.addAttribute("categoryList", list);
        categoryService.getCategoriesByClientUsername(oAuth2User).stream().forEach(categoryDTO -> System.out.println(categoryDTO.toString()));
        return "category/showAllCategories";
    }

    @GetMapping("/{id}")
    public String showCategoryPage(@PathVariable ("id") int id, Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {
        model.addAttribute("category", categoryService.getCategoryById(oAuth2User, id));
        model.addAttribute("expenseList",expenseService.getExpensesByUsernameAndCategory(id,oAuth2User));
       return "category/showCategory";
    }

    @PostMapping("/")
    public String addNewCategory(@AuthenticationPrincipal OAuth2User oAuth2User,
                                @ModelAttribute("category") CategoryDTO categoryDTO,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> System.out.println(error.toString()));
            return null;
        }
        categoryService.addNewCategory(oAuth2User,categoryDTO);
        return "redirect:/category/";
    }

    @GetMapping("/edit")
    public String editCategory(Model model, @RequestParam(name = "id") int id, @AuthenticationPrincipal OAuth2User oAuth2User,
                              @ModelAttribute("category") CategoryDTO categoryDTO) {
        try {
            model.addAttribute("category",categoryService.getCategoryById(oAuth2User,id));
//            model.addAttribute("categories",categoryService.getCategoriesByClientUsername(oAuth2User));
        }catch (CustomException e) {
            return "redirect:/category/";
        }
        return "category/editCategory";
    }

    @PostMapping("/update")
    public String updateCategory(@AuthenticationPrincipal OAuth2User oAuth2User,
                                @ModelAttribute("expense") CategoryDTO category,
                                @RequestParam("id")int id,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> System.out.println(error.toString()));
            return null;
        }
        categoryService.updateCategory(oAuth2User,category,id);
        return "redirect:/category/";
    }

    @GetMapping("/new")
    public String addCategoryForm(Model model, @AuthenticationPrincipal OAuth2User oAuth2User,
                                 @ModelAttribute("category") CategoryDTO categoryDTO ) {
        return "category/newCategoryForm";
    }

    @PostMapping("/delete")
    public String deleteCategory(Model model, @AuthenticationPrincipal OAuth2User oAuth2User,
                                @RequestParam(value = "cascade", required = false) Boolean cascade,
                                @RequestParam("id") Integer id) {
        categoryService.deleteCategory(oAuth2User,id,cascade);
        return "redirect:/category/";

    }

}
