package com.example.service.controller;

import com.example.service.dto.ClientUserDTO;
import com.example.service.dto.ExpenseDTO;
import com.example.service.entity.ClientUser;
import com.example.service.entity.Expense;
import com.example.service.exception.DuplicateException;
import com.example.service.service.ClientUserServiceImpl;
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
@RequestMapping("/expense")
@AllArgsConstructor
public class ExpenseClientController {

    private final ClientUserServiceImpl clientUserService;
    private final ExpenseService expenseService;

    @GetMapping("")
    public Expense getExpenseById(@RequestParam ("id") int expId) {
        return expenseService.getExpenseById(expId);
    }


    @ResponseBody
    @PatchMapping("")
    public ResponseEntity<ExpenseDTO> updateExpense(@RequestParam ("id") int expId,
                                                    @RequestBody ExpenseDTO expenseDTO) {
        ExpenseDTO updated = expenseService.update(expId,expenseDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public List<ExpenseDTO> getAllByClient(@PathVariable ("userId") int id){
        for (Expense expense :expenseService.getAllExpensesByClient(id)) {
            System.out.println(expense.toString());
        }
        return expenseService.getAllExpensesDTOByClient(id);
    }


    @ResponseBody
    @PostMapping("/{userId}")
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO,
                                                 @PathVariable ("userId") int id) {
        try {
            ExpenseDTO expenseDTO1 = expenseService.saveNewExpenseByClient(id,expenseDTO);
            return new ResponseEntity<>(expenseDTO1, HttpStatus.CREATED);
        } catch (DuplicateException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }


}
