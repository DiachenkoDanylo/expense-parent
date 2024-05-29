package com.example.service.controller;

import com.example.service.entity.Category;
import com.example.service.exception.NotFoundException;
import com.example.service.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public  Object getCategoryById(@PathVariable ("catId") int catId) {
        try {
            return categoryService.getCategoryById(catId);
        }catch (NotFoundException e){
            return new NotFoundException("Category with id :"+catId+" not found");
        }

    }
}
