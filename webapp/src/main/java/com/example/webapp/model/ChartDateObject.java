package com.example.webapp.model;
/*  expense-parent
    08.07.2024
    @author DiachenkoDanylo
*/


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartDateObject {

    public String startDate;
    public String endDate;
    public String category;
}
