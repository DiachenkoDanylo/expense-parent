package com.example.webapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*  expense-parent
    18.06.2024
    @author DiachenkoDanylo
*/
@Data
@NoArgsConstructor
public class ExpensePayload {

    private int category;

    private BigDecimal amount;

    private String description;

    public ExpensePayload(int category, BigDecimal amount, String description){
        this.amount=amount;
        this.description=description;
        this.category=category;
    }
}
