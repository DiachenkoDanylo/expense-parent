package com.example.service.entity;


/*  expense-parent
    28.05.2024
    @author DiachenkoDanylo
*/

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Integer id;

    @Column(nullable = false, unique = true,name = "c_name")
    private String name;

    @Column(nullable = false,name = "c_description")
    private String description;

    // Getters and setters
}