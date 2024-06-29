package com.example.service.service;

import com.example.service.dto.CategoryPayload;
import com.example.service.dto.ExpenseDTO;
import com.example.service.dto.ExpensePayloadCategory;
import com.example.service.entity.Category;
import com.example.service.entity.ClientUser;
import com.example.service.entity.Expense;
import com.example.service.exception.NotFoundException;
import com.example.service.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final ClientUserServiceImpl clientUserService;

    public List<Category>  getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getAllCategoriesByClient(String username) {
        ClientUser cl= clientUserService.getUserByUsername(username);
        System.out.println(cl.getUsername());
        return categoryRepository.findByClientUser_Username(cl.getUsername());
    }

    public Category getCategoryById(int catId) {
        if(categoryRepository.findById(catId).isPresent())
            return categoryRepository.findById(catId).get();
        throw new NotFoundException("The category with id :"+catId+" not found");
    }

    public Category saveNewCategoryByClient(String username, CategoryPayload categoryPayload) {
        try {
            ClientUser clientUser = clientUserService.getUserByUsername(username);
            Category category = convertToCategory(categoryPayload);
            category.setClientUser(clientUser);
            System.out.println(category.toString());
            categoryRepository.save(category);
//            categoryRepository.saveCategoryByClient(clientUserService.getUserByUsername(username).getId(),expense.getDescription(),expense.getCategory().getId(),expense.getAmount());
//            return convertToExpenseDTO(convertToExpenseWithCategory(expenseDTO));
            return category;
        } catch (NotFoundException e) {
            throw new NotFoundException("User with username '" + username + "' are not exists in our service");
        }
    }

    public Category convertToCategory (CategoryPayload categoryPayload) {
        return this.modelMapper.map(categoryPayload, Category.class);
    }


}
