package com.example.managerapp.model;
/*  expense-parent
    05.06.2024
    @author DiachenkoDanylo
*/

import lombok.Data;

@Data
public class CategoryDTO {

        private Integer id;
        private String name;
        private String description;


        public CategoryDTO(){}


        public CategoryDTO(Integer id ,String name, String description) {
                this.id=id;
                this.name =name;
                this.description = description;
        }


        public CategoryDTO(String name, String description) {
                this.name =name;
                this.description = description;
        }
}
