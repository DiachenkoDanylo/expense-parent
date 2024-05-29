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
    public ResponseEntity<ClientUserDTO> createUser(@RequestBody ClientUserDTO clientUserDTO) {

        try {
            ClientUser createdUser = clientUserService.convertToClientUser(clientUserDTO);
            createdUser = clientUserService.createClientUser(createdUser);
            return new ResponseEntity<>(clientUserService.convertToClientUserDTO(createdUser), HttpStatus.CREATED);
        } catch (DuplicateException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }



    @DeleteMapping
    public ResponseEntity<ClientUser> deleteUser(@RequestBody ClientUserDTO clientUserDTO) {

        try {
            clientUserService.deleteByUsername(clientUserDTO.getUsername());
            return new ResponseEntity<>(null,HttpStatus.OK);
        } catch (NotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientUserDTO> getUserById(@PathVariable("id") int id) {

        try {
            ClientUserDTO clientUserDTO = clientUserService.
                    convertToClientUserDTO(clientUserService.getUserById(id));
            return new ResponseEntity<>(clientUserDTO, HttpStatus.OK);
        } catch (NotFoundException e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientUserDTO> updateUser(@PathVariable("id") int id,
                                                    @RequestBody ClientUserDTO clientUserDTO) {
        ClientUserDTO updatedUser = clientUserService.updateUser(id, clientUserDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(DuplicateException.class)
//    public ResponseEntity<String> handleDuplicateUsernameException(DuplicateException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
//    }


}
