package com.example.managerapp.model;
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

    private int id;
    private BigDecimal amount;
    private String description;
    private LocalDateTime expenseDate;
    private CategoryDTO category;

    public ExpenseDTO(BigDecimal amount, String description,CategoryDTO category) {
        this.amount= amount;
        this.description=description;
        this.category =category;
    }


    public ExpenseDTO(int id, BigDecimal amount, String description,CategoryDTO category) {
        this.id=id;
        this.amount= amount;
        this.description=description;
        this.category =category;
    }
}
