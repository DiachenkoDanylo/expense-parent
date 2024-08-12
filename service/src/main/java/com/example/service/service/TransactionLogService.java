package com.example.service.service;

import com.example.service.entity.TransactionLog;
import com.example.service.repository.TransactionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*  expense-parent
    31.07.2024
    @author DiachenkoDanylo
*/
@Service
public class TransactionLogService {

    private final TransactionLogRepository repository;


    @Autowired
    public TransactionLogService(TransactionLogRepository repository) {
        this.repository = repository;
    }


    public void saveLog(TransactionLog log) {
        repository.save(log);
    }

    public List<String> getAllLogs() {
        repository.findAll().forEach(System.out::println);
        return repository.findAll().stream().map(TransactionLog::toString).toList();
    }

    public List<String> getLogsByUserUsername(String username) {
        return repository.findAllByDetailsContaining(username).stream()
                .map(TransactionLog::toString).toList();
    }

}
