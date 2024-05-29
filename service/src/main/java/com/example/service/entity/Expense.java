package com.example.service.entity;
/*  expense-parent
    28.05.2024
    @author DiachenkoDanylo
*/

import jakarta.persistence.*;
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
    private BigDecimal amount;

    @Column(name = "c_description")
    private String description;

    @Column(name = "c_expense_date")
    private LocalDateTime expenseDate;


}
