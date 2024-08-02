package com.example.webapp.service;
/*  expense-parent
    08.07.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.model.ExpenseDTO;

import java.util.List;

public interface ChartService {

    List<List<Object>> getListObjects(List<Object> objects);

    List<List<Object>> toChartData(List<ExpenseDTO> expenseDTOList);
}
