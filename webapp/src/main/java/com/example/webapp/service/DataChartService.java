package com.example.webapp.service;
/*  expense-parent
    08.07.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.model.ExpenseDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class DataChartService {

    private final ExpenseService expenseService;

    public LocalDateTime convertToDate(String date) {
        return LocalDateTime.of(Integer.parseInt(date.substring(0,4)),
                Integer.parseInt(date.substring(5,7)),
                Integer.parseInt(date.substring(8,10)),0,0);
    }

    public List<ExpenseDTO> filterByDate(List<ExpenseDTO> list,String startDate, String endDate) {
        return list.stream().filter(expenseDTO -> expenseDTO.getExpenseDate().isAfter(convertToDate(startDate))
                && expenseDTO.getExpenseDate().isBefore(convertToDate(endDate))).toList();
    }

    public List<ExpenseDTO> getDataByDate(OAuth2User oAuth2User,
                                           String startDate,
                                           String endDate,
                                           int category) {
        List<ExpenseDTO> expenseDTOList;
        if (category == 0) {
            expenseDTOList = expenseService.getExpensesByUsername(oAuth2User.getAttributes().get("email").toString());
        } else {
            expenseDTOList = expenseService.getExpensesByUsernameAndCategory(category, oAuth2User);
            expenseDTOList = filterByDate(expenseDTOList,startDate,endDate);
        }
        return expenseDTOList;
    }
}
