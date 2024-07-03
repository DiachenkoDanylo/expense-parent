package com.example.service.service;

import com.example.service.dto.ClientUserDTO;
import com.example.service.dto.ExpenseDTO;
import com.example.service.dto.ExpensePayloadCategory;
import com.example.service.entity.ClientUser;
import com.example.service.entity.Expense;
import com.example.service.exception.DuplicateException;
import com.example.service.exception.NotAllowedActionException;
import com.example.service.exception.NotFoundException;
import com.example.service.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/
@Service
@AllArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ClientUserServiceImpl clientUserService;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    public List<ExpenseDTO> getAllExpensesDTOByClient(String username){
        List<ExpenseDTO> expenseDTOS = new ArrayList<>();
        try {
            clientUserService.getUserByUsername(username);
            for(Expense expense : expenseRepository.findExpensesByClientUserUsername(username)) {
                expenseDTOS.add(convertToExpenseDTO(expense));
            }
            return expenseDTOS;
        }catch (NotFoundException e) {
            throw new NotFoundException("Username " + username + " are NOT exists in our service");
        }
    }

    public List<Expense> getAllExpensesByClient(String username){
        return expenseRepository.findExpensesByClientUserUsername(username);
    }

    public List<ExpenseDTO> getAllExpensesDTOByClientCategory(String username, int catId){
        List<ExpenseDTO> expenseDTOS = new ArrayList<>();
        for(Expense expense : expenseRepository.findExpensesByClientUserUsernameAndCategory_Id(username, catId)) {
            expenseDTOS.add(convertToExpenseDTO(expense));
        }
        return expenseDTOS;
    }

    public Expense getExpenseByUsernameAndId(int id, String username){
        try {
            Expense exp =  expenseRepository.findById(id).get();
            if(!exp.getClientUser().getUsername().equals(username)) {
                throw new NotAllowedActionException("The request is not allowed to user : "+username+" ");
            }else {
                return exp;
            }
        } catch (NotFoundException e) {
            throw new NotFoundException("The payment with this id : "+id+" not found");
        }
    }

    public Expense getExpenseById(int id){
        try {
            return expenseRepository.findById(id).get();
        } catch (NotFoundException e) {
            throw new NotFoundException("The payment with this id : "+id+" not found");
        }
    }


//    public ExpenseDTO saveNewExpenseByClient(String username, ExpenseDTO expenseDTO) {
//        if(expenseDTO.getCategory()==null) {
//            try {
//                ClientUser clientUser = clientUserService.getUserByUsername(username);
//                Expense expense = convertToExpense(expenseDTO);
//                expense.setClientUser(clientUser);
//                return convertToExpenseDTO(expenseRepository.save(expense));
//            } catch (NotFoundException e) {
//                throw new NotFoundException("User with username '" + username + "' are not exists in our service");
//            }
//        }else {
//            try {
//                ClientUser clientUser = clientUserService.getUserByUsername(username);
//                Expense expense = convertToExpenseWithCategory(expenseDTO);
//                expense.setClientUser(clientUser);
//                return convertToExpenseDTO(expenseRepository.saveExpenseByCategory(expense));
//            } catch (NotFoundException e) {
//                throw new NotFoundException("User with username '" + username + "' are not exists in our service");
//            }
//
//        }
//    }

    public ExpenseDTO update(int expId, ExpenseDTO expenseDTO,String username) {
        Expense exp = getExpenseByUsernameAndId(expId,username);
        exp.setExpenseDate(expenseDTO.getExpenseDate());
        exp.setAmount(expenseDTO.getAmount());
        exp.setDescription(expenseDTO.getDescription());
        exp.setCategory(expenseDTO.getCategory());
        exp.setExpenseDate(LocalDateTime.now());
        return convertToExpenseDTO(expenseRepository.save(exp));

    }

    public Expense convertToExpense(ExpenseDTO expenseDTO) {
        expenseDTO.setCategory(categoryService.getCategoryById(expenseDTO.getCategory().getId()));
        Expense res = this.modelMapper.map(expenseDTO, Expense.class);
        if (res.getExpenseDate()==null)
            res.setExpenseDate(LocalDateTime.now());
        return res;
    }

    public Expense convertToExpenseWithCategory(ExpensePayloadCategory expenseDTO) {
        ExpenseDTO expenseDTO1;
        if(expenseDTO.getCategory()==null) {
            expenseDTO1 = new ExpenseDTO(
                    expenseDTO.getId(),expenseDTO.getAmount(),expenseDTO.getDescription(),null);
        }else {
            expenseDTO1 = new ExpenseDTO(
                    expenseDTO.getId(),expenseDTO.getAmount(),expenseDTO.getDescription(),categoryService.getCategoryById(expenseDTO.getCategory().getId()));

        }
         Expense res = this.modelMapper.map(expenseDTO1, Expense.class);
        if (res.getExpenseDate()==null)
            res.setExpenseDate(LocalDateTime.now());
        return res;
    }

    public ExpenseDTO convertToExpenseDTO (Expense expense) {
        return this.modelMapper.map(expense, ExpenseDTO.class);
    }


    public ExpenseDTO saveNewExpenseByClientCategory(String username, ExpensePayloadCategory expenseDTO) {
        try {
            ClientUser clientUser = clientUserService.getUserByUsername(username);
            Expense expense = convertToExpenseWithCategory(expenseDTO);
            expense.setClientUser(clientUser);
            if(expense.getCategory()==null) {
                expenseRepository.saveExpenseByCategory(clientUserService.getUserByUsername(username).getId(),expense.getDescription(),null,expense.getAmount());
            }else {
                expenseRepository.saveExpenseByCategory(clientUserService.getUserByUsername(username).getId(),expense.getDescription(),expense.getCategory().getId(),expense.getAmount());
            }
            System.out.println(expense.toString());
            //expenseRepository.saveExpenseByCategory(clientUserService.getUserByUsername(username).getId(),expense.getDescription(),expense.getCategory().getId(),expense.getAmount());
            return convertToExpenseDTO(convertToExpenseWithCategory(expenseDTO));
        } catch (NotFoundException e) {
            throw new NotFoundException("User with username '" + username + "' are not exists in our service");
        }
    }

    public void deleteExpenseByUsernameAndId(String username, int expId) {
        if(getExpenseById(expId).getClientUser().getUsername().equals(username)) {
            expenseRepository.deleteById(expId);
        } else {
            throw new NotAllowedActionException("User with username "+username+" not allowed to do that");
        }
    }

    public void updateAfterDeletingCategory(String username, int catId, boolean include) {
        List<Expense> expenses = expenseRepository.findExpensesByClientUserUsernameAndCategory_Id(username,catId);
        if (include) {
            expenseRepository.deleteAll(expenses);
        }else{
            expenses.forEach(expense -> expense.setCategory(null));
            expenseRepository.saveAll(expenses);
        }
    }
}


