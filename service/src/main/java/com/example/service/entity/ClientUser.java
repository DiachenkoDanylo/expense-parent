package com.example.service.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/*  expense-parent
    28.05.2024
    @author DiachenkoDanylo
*/
@Table(name = "t_client_user")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Integer id;

    @Column(nullable = false, unique = true,name = "c_username")
    @Size(min = 3,max = 30,message = "Username name must be between 5 and 30 characters")
    private String username;

    @Column(name = "c_created_at",insertable = false)
    private LocalDateTime createdAt;

}
