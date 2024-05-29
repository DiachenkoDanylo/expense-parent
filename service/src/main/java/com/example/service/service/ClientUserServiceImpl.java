package com.example.service.service;
/*  expense-parent
    28.05.2024
    @author DiachenkoDanylo
*/

import com.example.service.dto.ClientUserDTO;
import com.example.service.entity.ClientUser;
import com.example.service.exception.DuplicateException;
import com.example.service.exception.NotFoundException;
import com.example.service.repository.ClientUserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class ClientUserServiceImpl implements  ClientUserService{

    private final ClientUserRepository clientUserRepository;
    private final ModelMapper modelMapper;


    public List<ClientUserDTO> getAllClientsDTO(){
        List<ClientUserDTO> clientUserDTOList = new ArrayList<>();
        for(ClientUser clientUser :clientUserRepository.findAll()) {
            clientUserDTOList.add( convertToClientUserDTO(clientUser));
        }
        return clientUserDTOList;
    }


    @Override
    public ClientUser getUserById(int userId) {
        if (clientUserRepository.findById(userId).isEmpty()){
            throw new NotFoundException("User with id: "+userId+" not found");
        }else {
            return this.clientUserRepository.findById(userId).get();
        }
    }

    public ClientUser getUserByUsername(String username) {
        if (clientUserRepository.findByUsername(username).isEmpty()){
            throw new NotFoundException("User with username : "+username+" not found");
        }else {
            ClientUser clientUser = this.clientUserRepository.findByUsername(username).get();
            return clientUser;
        }
    }


    @Override
    public ClientUser createClientUser(ClientUser clientUser) {
        if (clientUserRepository.findByUsername(clientUser.getUsername()).isPresent()) {
            throw new DuplicateException("Username '" + clientUser.getUsername() + "' already exists in our service");
        }
        return clientUserRepository.save(clientUser);
    }


    public ClientUser convertToClientUser(ClientUserDTO clientUserDTO) {
        return this.modelMapper.map(clientUserDTO, ClientUser.class);
    }

    public ClientUserDTO convertToClientUserDTO (ClientUser clientUser) {
        return this.modelMapper.map(clientUser, ClientUserDTO.class);
    }

    public ClientUser deleteByUsername(String username) {
        try {
            clientUserRepository.deleteByUsername(getUserByUsername(username).getUsername());
            return null;
        }catch (NotFoundException e){
            throw new NotFoundException("Username " + username + " are NOT exists in our service");
        }
    }

    public ClientUserDTO updateUser(int id, ClientUserDTO clientUserDTO) {
        try {
            ClientUser oldUser = getUserById(id);
            oldUser.setUsername(clientUserDTO.getUsername());
            createClientUser(oldUser);
            return convertToClientUserDTO(getUserById(id));
        }catch (NotFoundException e){
            throw new NotFoundException("User with id  " + id + " are NOT exists in our service");
        }
    }
}
