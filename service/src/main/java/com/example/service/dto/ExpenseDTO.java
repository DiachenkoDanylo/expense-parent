package com.example.service.dto;
/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class ExpenseDTO {

    private BigDecimal amount;

    private String description;

    private Category category;

    private LocalDateTime expenseDate;

    public ExpenseDTO() {
        super();
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDateTime expenseDate) {
        this.expenseDate = expenseDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
