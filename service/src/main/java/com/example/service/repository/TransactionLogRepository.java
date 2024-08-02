package com.example.service.repository;
/*  expense-parent
    31.07.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Integer> {

    List<TransactionLog> findAllByDetailsContaining(String username);
}
