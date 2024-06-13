package com.example.service.controller;

import com.example.service.dto.ClientUserDTO;
import com.example.service.entity.ClientUser;
import com.example.service.exception.DuplicateException;
import com.example.service.exception.NotFoundException;
import com.example.service.service.ClientUserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*  expense-parent
    28.05.2024
    @author DiachenkoDanylo
*/
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class ClientUserController {

    private final ClientUserServiceImpl clientUserService;

    @GetMapping("")
    public List<ClientUserDTO> getClients(){
        return clientUserService.getAllClientsDTO();
    }

    @ResponseBody
    @PostMapping()
    public ClientUserDTO createUser(@RequestBody ClientUserDTO clientUserDTO) {
        ClientUser createdUser = clientUserService.convertToClientUser(clientUserDTO);
        createdUser = clientUserService.createClientUser(createdUser);
        return clientUserService.convertToClientUserDTO(createdUser);
    }

    @DeleteMapping
    public ClientUser deleteUser(@RequestBody ClientUserDTO clientUserDTO) {
        return clientUserService.deleteByUsername(clientUserDTO.getUsername());
    }

    @GetMapping("/{username}")
    public ClientUserDTO getUserByUsername(@PathVariable("username") String username) {
        return clientUserService.
                convertToClientUserDTO(clientUserService.getUserByUsername(username));
    }

    @PatchMapping("/{username}")
    public ClientUserDTO updateUser(@PathVariable("username") String username,
                                                    @RequestBody ClientUserDTO clientUserDTO) {
        return clientUserService.updateUser(username, clientUserDTO);
    }

}
