package com.example.service.service;

import com.example.service.entity.Category;
import com.example.service.exception.NotFoundException;
import com.example.service.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/
@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category>  getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int catId) {
        if(categoryRepository.findById(catId).isPresent())
            return categoryRepository.findById(catId).get();
        throw new NotFoundException("The category with id :"+catId+" not found");
    }
}
