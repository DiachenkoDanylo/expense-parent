package com.example.service.dto;
/*  expense-parent
    25.06.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ExpensePayloadCategory {

    private int id;

    private Category category;

    private BigDecimal amount;

    private String description;

    private LocalDateTime expenseDate;


    public ExpensePayloadCategory(LocalDateTime expenseDate,int id,Category category, BigDecimal amount, String description){
        this.id=id;
        this.expenseDate = expenseDate;
        this.amount=amount;
        this.description=description;
        this.category=category;
    }
}
