package com.example.managerapp.model;

import lombok.Data;

import java.time.LocalDateTime;

/*  expense-parent
    14.06.2024
    @author DiachenkoDanylo
*/
@Data
public class ClientUserDTO {

    private Integer id;

    private String username;

    private LocalDateTime createdAt;

    public ClientUserDTO(){

    }

    public ClientUserDTO(Integer id, String username, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
    }
}
