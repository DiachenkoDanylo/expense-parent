package com.example.webapp.model;
/*  expense-parent
    08.06.2024
    @author DiachenkoDanylo
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {

    private Integer id;

    private BigDecimal amount;

    private String description;

    private LocalDateTime expenseDate;

    private int categoryId;

    public ExpenseDTO(BigDecimal amount, String description,int categoryId) {
        this.amount= amount;
        this.description=description;
        this.categoryId =categoryId;
    }
}
