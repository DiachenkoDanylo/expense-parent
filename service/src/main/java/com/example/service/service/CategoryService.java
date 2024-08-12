package com.example.service.service;

import com.example.service.dto.CategoryPayload;
import com.example.service.entity.Category;
import com.example.service.entity.ClientUser;
import com.example.service.exception.NotAllowedActionException;
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
            return category;
        } catch (NotFoundException e) {
            throw new NotFoundException("User with username '" + username + "' are not exists in our service");
        }
    }

    public Category updateCategory(String username, CategoryPayload categoryPayload) {
        try {
            if(categoryRepository.findById(categoryPayload.getId()).get().getClientUser().getUsername().equals(username)) {
                Category category= categoryRepository.findById(categoryPayload.getId()).get();
                category.setDescription(categoryPayload.getDescription());
                category.setName(categoryPayload.getName());
                categoryRepository.save(category);
                return category;
            }else {
                throw new NotAllowedActionException("not allowed");
            }
        } catch (NotFoundException e) {
            throw new NotFoundException("User with username '" + username + "' are not exists in our service");
        }
    }

    public Category convertToCategory (CategoryPayload categoryPayload) {
        return this.modelMapper.map(categoryPayload, Category.class);
    }


    public void deleteCategoryByUsernameAndId(String username, int catId, boolean include) {
        if(this.getCategoryById(catId).getClientUser().getUsername().equals(username)){
             categoryRepository.deleteById(catId);
             System.out.println("not INCLUDE AT DELETING");
            }
    }

}
