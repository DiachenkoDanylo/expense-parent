package com.example.webapp.controller;

import com.example.webapp.exception.CustomException;
import com.example.webapp.model.CategoryDTO;
import com.example.webapp.model.ExpenseDTO;
import com.example.webapp.model.ExpensePayload;
import com.example.webapp.service.CategoryService;
import com.example.webapp.service.ClientUserService;
import com.example.webapp.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
//        clientUserService.getUsername(oAuth2User.getAttributes().get("email").toString()).stream();

        model.addAttribute("category", categoryService.getCategoryById(oAuth2User, id));
        model.addAttribute("expenseList",expenseService.getExpensesByUsernameAndCategory(id,oAuth2User));
        //model.addAttribute("ExpenseList", expenseService.getAllByCategoryAndUser(oAuth2User.getAttributes().get("email").toString(),id));
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
        System.out.println("\n \n \n"+category.toString()+"\n \n \n ");
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
        System.out.println(cascade);
        System.out.println("Id of deleting card is "+id);
        categoryService.deleteCategory(oAuth2User,id,cascade);

        //model.addAttribute("categories",dtoList);
        return "redirect:/category/";
//                "expense/newExpenseForm";
    }

}
