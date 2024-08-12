package com.example.service.dto;
/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExpenseDTO {

    private int id;
    private Category category;
    private BigDecimal amount;
    private String description;
    private LocalDateTime expenseDate;


    public ExpenseDTO() {
        super();
    }


    public ExpenseDTO(int id, BigDecimal amount, String description, Category categoryById) {
        this.id=id;
        this.amount=amount;
        this.category=categoryById;
        this.description=description;
    }
}
