package com.example.service.repository;
/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Integer> {


    List<Expense> findExpensesByClientUserUsername(String username);
    List<Expense> findExpensesByClientUserId(int id);

    List<Expense> findExpensesByClientUserUsernameAndCategory_Id(String username, int id);
    List<Expense> findExpensesByClientUserIdAndCategoryId(int id, int catId);
}
