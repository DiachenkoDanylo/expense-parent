package com.example.service.entity;
/*  expense-parent
    28.05.2024
    @author DiachenkoDanylo
*/

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "c_client_user_id", nullable = false)
    private ClientUser clientUser;

    @ManyToOne
    @JoinColumn(name = "c_category_id")
    private Category category;

    @Column(nullable = false, name = "c_amount")
    @Min(value = 1, message = "Expense must be bigger than 1")
    @Max(value = 999999, message = "Expense must be lower than 1000000")
    private BigDecimal amount;

    @Column(name = "c_description")
    @Size(max = 30 ,message = "Description should have less than 30 characters")
    private String description;

    @Column(name = "c_expense_date")
    private LocalDateTime expenseDate;


}
