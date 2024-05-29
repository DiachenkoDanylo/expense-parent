package com.example.service.repository;
/*  expense-parent
    28.05.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.ClientUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientUserRepository extends JpaRepository<ClientUser,Integer> {
    Optional<ClientUser> findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);


}
