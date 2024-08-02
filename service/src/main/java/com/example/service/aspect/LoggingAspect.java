package com.example.service.aspect;
/*  expense-parent
    31.07.2024
    @author DiachenkoDanylo
*/

import com.example.service.entity.TransactionLog;
import com.example.service.service.TransactionLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class LoggingAspect {

    private final TransactionLogService logService;

    @Autowired
    public LoggingAspect(TransactionLogService logService) {
        this.logService = logService;
    }

    @Pointcut("execution(* com.example.service.repository..*(..)) && !execution(* com.example.service.repository.TransactionLogRepository.*(..))")
    public void allRepositoriesExceptTransactionLogRepo() {}

    @Around("allRepositoriesExceptTransactionLogRepo() && execution(* save(..))")
    public Object logSaveOrUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object entity = joinPoint.getArgs()[0];
        String operation;

        // Determine if the entity is new (insert) or existing (update)
        if (isNewEntity(entity)) {
            operation = "INSERT";
        } else {
            operation = "UPDATE";
        }

        Object result = joinPoint.proceed(); // Proceed with the actual save or update

        // Log the transaction
        saveTransactionLog(operation, entity);

        return result;
    }

    @After("allRepositoriesExceptTransactionLogRepo() && execution(* delete*(..))")
    public void logAfterDelete(JoinPoint joinPoint) {
        Object entity = joinPoint.getArgs()[0];
        saveTransactionLog("DELETE", entity);
    }

    private void saveTransactionLog(String operation, Object entity) {
        TransactionLog log = new TransactionLog();
        log.setOperation(operation);
        log.setEntityName(entity.getClass().getSimpleName());
        log.setEntityId(getEntityId(entity));
        log.setDetails(entity.toString());
        log.setTimestamp(LocalDateTime.now());
        logService.saveLog(log);
    }

    private Integer getEntityId(Object entity) {
        try {
            return (Integer) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isNewEntity(Object entity) {
        try {
            Integer id = getEntityId(entity);
            return id == null || id == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
