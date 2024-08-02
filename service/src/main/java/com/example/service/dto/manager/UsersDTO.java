package com.example.service.dto.manager;
/*  expense-parent
    21.07.2024
    @author DiachenkoDanylo
*/

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Data
public class UsersDTO {

    private Integer id;

    private String username;

    private LocalDateTime createdAt;


    public UsersDTO(Integer id, String username, LocalDateTime createdAt) {
        this.id=id;
        this.username=username;
        this.createdAt=createdAt;
    }
}
