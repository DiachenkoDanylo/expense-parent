package com.example.service.service;

import com.example.service.entity.ClientUser;

/*  expense-parent
    28.05.2024
    @author DiachenkoDanylo
*/


public interface ClientUserService {

    ClientUser getUserByUsername(String username);

    ClientUser createClientUser(ClientUser user);

}
