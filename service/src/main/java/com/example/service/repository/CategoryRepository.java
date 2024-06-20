package com.example.service.repository;
/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.Category;
import com.example.service.entity.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findAllByClientUser(ClientUser clientUser);
    List<Category> findByClientUser_Username(String username);
//    List<Category> findAllByClientUser_Username
}
