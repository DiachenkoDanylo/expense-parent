package com.example.service.controller;

import com.example.service.dto.ClientUserDTO;
import com.example.service.dto.ExpenseDTO;
import com.example.service.dto.ExpensePayloadCategory;
import com.example.service.entity.ClientUser;
import com.example.service.entity.Expense;
import com.example.service.exception.DuplicateException;
import com.example.service.exception.NotAllowedActionException;
import com.example.service.service.ClientUserServiceImpl;
import com.example.service.service.ExpenseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/*  expense-parent
    29.05.2024
    @author DiachenkoDanylo
*/
@RestController
@RequestMapping("/expense")
@AllArgsConstructor
@Slf4j
public class ExpenseClientController {

    private final ClientUserServiceImpl clientUserService;
    private final ExpenseService expenseService;
    private final WebApplicationContext webApplicationContext;

    @GetMapping("")
    public Expense getExpenseById(@RequestParam ("id") int expId) {
        return expenseService.getExpenseById(expId);
    }



    @GetMapping("/{username}")
    public List<ExpenseDTO> getAllByClient(@PathVariable ("username") String username) {
        return expenseService.getAllExpensesDTOByClient(username);
    }

    @GetMapping("/{username}/")
    public Expense getExpenseByUsernameAndId(@RequestParam ("id") int expId,
                                             @PathVariable("username") String username) {

            return expenseService.getExpenseByUsernameAndId(expId,username);

    }
//    @GetMapping("/{username}")
//    public List<ExpenseDTO> getAllByClient(@PathVariable ("username") String username, @AuthenticationPrincipal OAuth2User oAuth2User) {
//        System.out.println("SELECTED \n @AUTHENTICATION PRINCIPAL \n \n \n "+ oAuth2User.getAttributes().toString());
//        return expenseService.getAllExpensesDTOByClient(username);
//    }

    @GetMapping("/{username}/category/{categoryId}")
    public List<ExpenseDTO> getAllByClientAndCategory(@PathVariable ("username") String username,
                                                      @PathVariable ("categoryId") int catId) {
        return expenseService.getAllExpensesDTOByClientCategory(username, catId);
    }

    @ResponseBody
    @PostMapping("/{username}")
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpensePayloadCategory expenseDTO,
                                                 @PathVariable ("username") String username) {
        ExpenseDTO expenseDTO1 = expenseService.saveNewExpenseByClientCategory(username,expenseDTO);
        return new ResponseEntity<>(expenseDTO1, HttpStatus.CREATED);
    }
    @ResponseBody
    @PatchMapping("/{username}")
    public ResponseEntity<ExpenseDTO> updateExpense(@RequestParam ("id") int expId,
                                                    @RequestBody ExpenseDTO expenseDTO,
                                                    @PathVariable ("username") String username) {
        System.out.println("\n \n \n "+"expense ID" +
                expId+"\n"+"expense DTO " +expenseDTO.toString()+"\n username is"+username);
        ExpenseDTO updated = expenseService.update(expId,expenseDTO,username);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

}
