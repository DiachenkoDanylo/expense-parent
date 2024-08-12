package com.example.service.controller;

import com.example.service.dto.ExpenseDTO;
import com.example.service.dto.ExpensePayloadCategory;
import com.example.service.entity.Expense;
import com.example.service.service.ClientUserServiceImpl;
import com.example.service.service.ExpenseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/
@RestController
@RequestMapping("/expense")
@AllArgsConstructor
@Slf4j
public class ExpenseClientController {

    private final ExpenseService expenseService;


    @GetMapping("")
    public Expense getExpenseById(@RequestParam ("id") int expId) {
        return expenseService.getExpenseById(expId);
    }

    @GetMapping("/{username}")
    public List<ExpenseDTO> getAllByClient(@PathVariable ("username") String username) {
        return expenseService.getAllExpensesDTOByClient(username);
    }

    @GetMapping("/{username}/")
    public Expense getExpenseByUsernameAndId(@RequestParam ("id") int expId,
                                             @PathVariable("username") String username) {
            return expenseService.getExpenseByUsernameAndId(expId,username);
    }

    @GetMapping("/{username}/category/{categoryId}")
    public List<ExpenseDTO> getAllByClientAndCategory(@PathVariable ("username") String username,
                                                      @PathVariable ("categoryId") Integer catId) {
        return expenseService.getAllExpensesDTOByClientCategory(username, catId);
    }

    @ResponseBody
    @PostMapping("/{username}")
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpensePayloadCategory expenseDTO,
                                                 @PathVariable ("username") String username) {
        ExpenseDTO expenseDTO1 = expenseService.saveNewExpenseByClientCategory(username,expenseDTO);
        return new ResponseEntity<>(expenseDTO1, HttpStatus.CREATED);
    }

    @ResponseBody
    @PatchMapping("/{username}")
    public ResponseEntity<ExpenseDTO> updateExpense(@RequestParam ("id") int expId,
                                                    @RequestBody ExpenseDTO expenseDTO,
                                                    @PathVariable ("username") String username) {
        ExpenseDTO updated = expenseService.update(expId,expenseDTO,username);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteExpense(@RequestParam ("id") int expId,
                                                    @PathVariable ("username") String username) {
        expenseService.deleteExpenseByUsernameAndId(username,expId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
