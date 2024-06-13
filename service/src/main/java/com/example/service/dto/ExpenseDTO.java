package com.example.service.dto;
/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
@Data
public class ExpenseDTO {

    private Integer id;

    private BigDecimal amount;

    private String description;

    private LocalDateTime expenseDate;

    public ExpenseDTO() {
        super();
    }

}
