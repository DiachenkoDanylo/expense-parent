package com.example.service.service;

import com.example.service.dto.ClientUserDTO;
import com.example.service.entity.ClientUser;
import com.example.service.repository.ClientUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*  expense-parent
    28.05.2024
    @author DiachenkoDanylo
*/


public interface ClientUserService {

    ClientUser getUserById(int userId);

    ClientUser createClientUser(ClientUser user);

}
