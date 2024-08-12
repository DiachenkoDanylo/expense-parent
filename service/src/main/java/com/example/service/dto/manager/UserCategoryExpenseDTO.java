package com.example.service.dto.manager;
/*  expense-parent
    23.07.2024
    @author DiachenkoDanylo
*/

import com.example.service.dto.CategoryPayload;
import com.example.service.dto.ExpenseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserCategoryExpenseDTO {

    private Integer id;
    private String username;
    private LocalDateTime createdAt;
    private List<CategoryPayload> categoryList;
    private List<ExpenseDTO> expenseList;

    public UserCategoryExpenseDTO(Integer id, String username, LocalDateTime createdAt) {
        this.id=id;
        this.username=username;
        this.createdAt=createdAt;

    }
}
