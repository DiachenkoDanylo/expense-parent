package com.example.service.service;
/*  expense-parent
    06.06.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.Category;
import com.example.service.entity.ClientUser;
import com.example.service.entity.Expense;
import com.example.service.repository.ExpenseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository repository;

    @InjectMocks
    private ExpenseService expenseService;


    @Test
    @DisplayName("Testing get all expenses by 1 client")
    public void getAllExpensesByClient() {
        List<Expense> expenses = setUpdb();
        List<Expense> expenseForOneClient = expenses.stream().filter(expense -> expense.getClientUser().getId() == 1).toList();
       // System.out.println(expenseForOneClient);
        Mockito.when(repository.findExpensesByClientUserIdOrderByExpenseDateDesc(1)).thenReturn(expenseForOneClient);
        List<Expense> result = expenseService.getAllExpensesByClient("Oleg_Skripka");
        Assertions.assertEquals(expenseForOneClient,result);
    }


    @Test
    @DisplayName("Testing get all expenses by 1 client -> empty")
    public void getAllExpensesByClient_null_empty() {
        List<Expense> expenses = setUpdb();
        List<Expense> expenseForOneClient = expenses.stream().filter(expense -> Objects.equals(expense.getClientUser().getUsername(), "Oleg_Skripka")).toList();
        Mockito.when(repository.findExpensesByClientUserUsernameOrderByExpenseDateDesc("Oleg_Skripka")).thenReturn(expenseForOneClient);
        List<Expense> result = expenseService.getAllExpensesByClient("Oleg_Skripka");
        Assertions.assertEquals(new ArrayList<>(),result);
    }

    @Test
    @DisplayName("testing get expensebyId")
    void getExpenseById() {
        Expense expected  = new Expense(2,new ClientUser(2,"user2",LocalDateTime.of(1010,10,10,10,10)),new Category(2, new ClientUser(),"category2","category2 description"), new BigDecimal( 250),"expense2 desc", LocalDateTime.of(1010,10,10,10,10));
        Mockito.when(repository.findById(2)).thenReturn(setUpdb().stream().filter(expense -> expense.getId() == 2).toList().stream().findFirst());
        Assertions.assertEquals(expected,repository.findById(2).get());
    }



    private List<Expense> setUpdb() {
//        Expense expense1  = new Expense(1,new ClientUser(1,"user1",LocalDateTime.now().minusDays(5)),new Category(1,"category1","category1 description"), new BigDecimal( 150),"expense1 desc", LocalDateTime.now().minusHours(5));
//        Expense expense2  = new Expense(2,new ClientUser(2,"user2",LocalDateTime.of(1010,10,10,10,10)),new Category(2,"category2","category2 description"), new BigDecimal( 250),"expense2 desc",LocalDateTime.of(1010,10,10,10,10));
//        Expense expense3  = new Expense(3,expense2.getClientUser(),expense1.getCategory(), new BigDecimal( 1111),"desc3", LocalDateTime.now().minusHours(3));
//        Expense expense4  = new Expense(4,expense1.getClientUser(),new Category(3,expense1.getClientUser(),"category3","category3 desc"), new BigDecimal("888.23"),"desc4", LocalDateTime.now().minusHours(2));
//        Expense expense5  = new Expense(5,new ClientUser(3,"user3",LocalDateTime.now().minusDays(2)), expense1.getCategory(), new BigDecimal( 1422),"desc5", LocalDateTime.now().minusHours(1));
//        return List.of(expense1,expense2,expense3,expense4,expense5);
        return null;
    }
}
