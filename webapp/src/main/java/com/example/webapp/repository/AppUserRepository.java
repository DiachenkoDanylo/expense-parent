package com.example.webapp.repository;
/*  expense-parent
    28.05.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    AppUser findByUsername(String username);
}
