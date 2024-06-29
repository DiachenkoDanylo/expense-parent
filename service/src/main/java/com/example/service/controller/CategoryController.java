package com.example.service.controller;

import com.example.service.dto.CategoryPayload;
import com.example.service.dto.ExpenseDTO;
import com.example.service.dto.ExpensePayloadCategory;
import com.example.service.entity.Category;
import com.example.service.exception.NotFoundException;
import com.example.service.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{username}")
    public List<Category> getAllByClientUser(@PathVariable ("username") String username) {
        return categoryService.getAllCategoriesByClient(username);
    }

    @GetMapping("{username}/{catId}")
    public  Category getCategoryById(@PathVariable ("catId") int catId, @PathVariable ("username")String username) {
        return categoryService.getCategoryById(catId);
    }

    @ResponseBody
    @PostMapping("/{username}")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryPayload categoryPayload,
                                                    @PathVariable ("username") String username) {
        return new ResponseEntity<>(categoryService.saveNewCategoryByClient(username,categoryPayload), HttpStatus.CREATED);
    }
}
