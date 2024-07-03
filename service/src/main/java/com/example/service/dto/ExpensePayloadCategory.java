package com.example.service.dto;
/*  expense-parent
    25.06.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ExpensePayloadCategory {

    private int id;

    private Category category;

    private BigDecimal amount;

    private String description;


    public ExpensePayloadCategory(int id,Category category, BigDecimal amount, String description){
        this.id=id;
        this.amount=amount;
        this.description=description;
        this.category=category;
    }
}
