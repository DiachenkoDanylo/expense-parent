package com.example.managerapp.model;
/*  expense-parent
    10.08.2024
    @author DiachenkoDanylo
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelpTicketDTO {

    private String id;
    private String user;
    private String manager;
    private String solved;

}
