package com.example.service.dto;
/*  expense-parent
    28.06.2024
    @author DiachenkoDanylo
*/

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryPayload {

    private int id;
    private String name;
    private String description;


    public CategoryPayload(int id, String name, String description){
        this.id=id;
        this.name=name;
        this.description=description;
    }
}
