package com.example.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/*  expense-parent
    08.07.2024
    @author DiachenkoDanylo
*/
@AllArgsConstructor
@Data
public class DateForm {
    public LocalDateTime startDate;
    public  LocalDateTime endDate;
    public Integer categoryId;
}
