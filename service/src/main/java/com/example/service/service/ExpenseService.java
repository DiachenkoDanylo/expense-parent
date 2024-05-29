package com.example.service.service;

import com.example.service.dto.ClientUserDTO;
import com.example.service.dto.ExpenseDTO;
import com.example.service.entity.ClientUser;
import com.example.service.entity.Expense;
import com.example.service.exception.DuplicateException;
import com.example.service.exception.NotFoundException;
import com.example.service.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
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

    public List<ExpenseDTO> getAllExpensesDTOByClient(int id){
        List<ExpenseDTO> expenseDTOS = new ArrayList<>();
        for(Expense expense : expenseRepository.findExpensesByClientUserId(id)) {
            expenseDTOS.add(convertToExpenseDTO(expense));
        }
        return expenseDTOS;
    }

    public List<Expense> getAllExpensesByClient(int id){
        for (Expense expense :expenseRepository.findExpensesByClientUserId(id)) {
            System.out.println(expense.toString());
        }
        return expenseRepository.findExpensesByClientUserId(id);

    }

    public Expense getExpenseById(int id){
        if (expenseRepository.findById(id).isPresent())
            return expenseRepository.findById(id).get();
        throw new NotFoundException("The payment with this id : "+id+" not found");
    }

    public ExpenseDTO saveNewExpenseByClient(int id, ExpenseDTO expenseDTO) {
        try {
            ClientUser clientUser = clientUserService.getUserById(id);
            Expense expense = convertToExpense(expenseDTO);
            System.out.println(expense.toString());
            expense.setClientUser(clientUser);
            System.out.println(expense.toString()+" expense after got client");
            return convertToExpenseDTO(expenseRepository.save(expense));
        } catch (NotFoundException e) {
            throw new NotFoundException("User with id '" + id + "' are not exists in our service");
        }
    }

    public ExpenseDTO update(int expId, ExpenseDTO expenseDTO) {
        Expense exp = getExpenseById(expId);
        exp.setExpenseDate(expenseDTO.getExpenseDate());
        exp.setAmount(expenseDTO.getAmount());
        exp.setCategory(expenseDTO.getCategory());
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


