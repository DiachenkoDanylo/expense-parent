package com.example.webapp.service;
/*  expense-parent
    17.07.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.model.ExpenseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class ChartServiceImplColumn implements ChartService{

    @Override
    public List<List<Object>> getListObjects(List<Object> objects) {
        return List.of();
    }

    @Override
    public List<List<Object>> toChartData(List<ExpenseDTO> expenseDTOList) {
        List<List<Object>> list  = new ArrayList<>();
        for(ExpenseDTO expenseDTO : expenseDTOList) {
            list.add(List.of(expenseDTO.getExpenseDate().toLocalDate().toString(),expenseDTO.getAmount()));
        }
        return list;
    }
}
