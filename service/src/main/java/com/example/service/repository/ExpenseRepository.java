package com.example.service.repository;
/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Integer> {


    List<Expense> findExpensesByClientUserUsername(String username);
    List<Expense> findExpensesByClientUserId(int id);

    List<Expense> findExpensesByClientUserUsernameAndCategory_Id(String username, int id);
    @Query(
            value = " INSERT INTO t_expense(c_client_user_id,  c_description, c_category_id, c_amount) VALUES (?,?,?,?)",
            nativeQuery = true)
    void saveExpenseByCategory(Integer a, String b, Integer c,BigDecimal d);
    List<Expense> findExpensesByClientUserIdAndCategoryId(int id, int catId);

}
