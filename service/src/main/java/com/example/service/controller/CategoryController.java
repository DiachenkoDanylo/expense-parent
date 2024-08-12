package com.example.service.controller;

import com.example.service.dto.CategoryPayload;
import com.example.service.entity.Category;
import com.example.service.exception.NotAllowedActionException;
import com.example.service.service.CategoryService;
import com.example.service.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final ExpenseService expenseService;


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
        if(categoryService.getCategoryById(catId).getClientUser().getUsername().equals(username)) {
            return categoryService.getCategoryById(catId);
        }else {
            throw new NotAllowedActionException("not allowed");
        }
    }

    @ResponseBody
    @PostMapping("/{username}")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryPayload categoryPayload,
                                                    @PathVariable ("username") String username) {
        return new ResponseEntity<>(categoryService.saveNewCategoryByClient(username,categoryPayload), HttpStatus.CREATED);
    }

    @ResponseBody
    @PatchMapping("/{username}")
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryPayload categoryPayload,
                                                   @PathVariable ("username") String username) {

        return new ResponseEntity<>(categoryService.updateCategory(username,categoryPayload), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteCategory(@RequestParam ("id") int catId,
                                                @PathVariable ("username") String username,
                                                 @RequestParam (value = "with", required = false) boolean with) {
        categoryService.deleteCategoryByUsernameAndId(username,catId,with);
        expenseService.updateAfterDeletingCategory(username,catId,with);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
