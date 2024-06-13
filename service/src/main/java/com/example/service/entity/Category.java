package com.example.service.entity;


/*  expense-parent
    28.05.2024
    @author DiachenkoDanylo
*/

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3,max = 30,message = "Category name must be between 3 and 30 characters")
    private String name;

    @Column(nullable = false,name = "c_description")
    @Size(min = 10,max = 200,message = "Category description  name must be between 10 and 200 characters")
    private String description;

    // Getters and setters
}