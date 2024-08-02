package com.example.webapp.model;
/*  expense-parent
    05.06.2024
    @author DiachenkoDanylo
*/

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {

        private Integer id;

        @Size(min = 3,max = 30,message = "Category name must be between 3 and 30 characters")
        private String name;

        @Size(min = 10,max = 200,message = "Category desc must be between 10 and 200 characters")
        private String description;

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
