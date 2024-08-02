package com.example.service.controller;
/*  expense-parent
    28.07.2024
    @author DiachenkoDanylo
*/

import com.example.service.service.ClientUserServiceImpl;
import com.example.service.service.TransactionLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/audit/")
@AllArgsConstructor
public class AuditController {

    private final ClientUserServiceImpl clientUserServiceImpl;
    private  final TransactionLogService transactionLogService;


    @GetMapping("users/")
    public List<String> getAllAuditLogs() {

        return transactionLogService.getAllLogs();
    }

    @GetMapping("users/{userId}")
    public List<String> getUserAuditLogs(@PathVariable("userId") Integer userId) {
        String username = clientUserServiceImpl.getUsernameUserById(userId);
        transactionLogService.getLogsByUserUsername(username).forEach(object -> System.out.println("\n \n \n"+ object.toString()));
        return transactionLogService.getLogsByUserUsername(username);

    }
}
