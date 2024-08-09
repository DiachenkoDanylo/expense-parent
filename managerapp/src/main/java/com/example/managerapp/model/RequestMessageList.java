package com.example.managerapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*  expense-parent
    09.08.2024
    @author DiachenkoDanylo
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMessageList {

    private String sendFrom;
    private String ticketId;
}
