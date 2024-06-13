package com.example.service.service;

import com.example.service.dto.ClientUserDTO;
import com.example.service.dto.ExpenseDTO;
import com.example.service.entity.ClientUser;
import com.example.service.entity.Expense;
import com.example.service.exception.DuplicateException;
import com.example.service.exception.NotFoundException;
import com.example.service.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<ExpenseDTO> getAllExpensesDTOByClient(String username){
        List<ExpenseDTO> expenseDTOS = new ArrayList<>();
        try {
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

    public Expense getExpenseById(int id){
        if (expenseRepository.findById(id).isPresent())
            return expenseRepository.findById(id).get();
        throw new NotFoundException("The payment with this id : "+id+" not found");
    }

    public ExpenseDTO saveNewExpenseByClient(String username, ExpenseDTO expenseDTO) {
        try {
            ClientUser clientUser = clientUserService.getUserByUsername(username);
            Expense expense = convertToExpense(expenseDTO);
            expense.setClientUser(clientUser);
            return convertToExpenseDTO(expenseRepository.save(expense));
        } catch (NotFoundException e) {
            throw new NotFoundException("User with username '" + username + "' are not exists in our service");
        }
    }

    public ExpenseDTO update(int expId, ExpenseDTO expenseDTO) {
        Expense exp = getExpenseById(expId);
        exp.setExpenseDate(expenseDTO.getExpenseDate());
        exp.setAmount(expenseDTO.getAmount());
        exp.setDescription(expenseDTO.getDescription());
        return convertToExpenseDTO(expenseRepository.save(exp));

    }

    public Expense convertToExpense(ExpenseDTO expenseDTO) {
        return this.modelMapper.map(expenseDTO, Expense.class);
    }

    public ExpenseDTO convertToExpenseDTO (Expense expense) {
        return this.modelMapper.map(expense, ExpenseDTO.class);
    }


}


