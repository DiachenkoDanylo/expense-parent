package com.example.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/*  expense-parent
    18.06.2024
    @author DiachenkoDanylo
*/
@Data
@AllArgsConstructor
public class ExpensePayload {

    private BigDecimal amount;

    private String description;
}
