package com.example.service.repository;
/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
