package com.example.service.repository;
/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Integer> {

    List<Expense> findExpensesByClientUserUsernameOrderByExpenseDateDesc(String username);
    List<Expense> findExpensesByClientUserIdOrderByExpenseDateDesc(int id);
    List<Expense> findExpensesByClientUserUsernameAndCategory_IdOrderByExpenseDateDesc(String username, Integer id);
    List<Expense> findExpensesByClientUserIdAndCategoryIsNullOrderByExpenseDateDesc(Integer id);
    void deleteExpenseByIdAndClientUser_Id(int id, int userId);
}
