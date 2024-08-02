package com.example.service.entity;
/*  expense-parent
    31.07.2024
    @author DiachenkoDanylo
*/

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_transaction_log")
@Data
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Integer id;

    @Column(name = "c_operation")
    private String operation;

    @Column(name = "c_entity_name")
    private String entityName;

    @Column(name = "c_entity_id")
    private Integer entityId;

    @Column(name = "c_details")
    private String details;

    @Column(name = "c_timestamp")
    private LocalDateTime timestamp;

}
