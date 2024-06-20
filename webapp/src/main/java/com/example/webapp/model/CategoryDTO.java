package com.example.webapp.model;
/*  expense-parent
    05.06.2024
    @author DiachenkoDanylo
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
        private Integer id;
        private String name;
        private String description;

        public CategoryDTO(String name, String description) {
                this.name =name;
                this.description = description;
        }
}
