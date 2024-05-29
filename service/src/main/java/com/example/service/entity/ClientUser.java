package com.example.service.entity;

import jakarta.persistence.*;
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
    private String username;

    @Column(name = "c_created_at",insertable = false)
    private Date createdAt;

}
