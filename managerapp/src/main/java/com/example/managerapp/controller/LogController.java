package com.example.managerapp.controller;

import com.example.managerapp.model.ClientUserDTO;
import com.example.managerapp.service.manager.LogService;
import com.example.managerapp.service.manager.UsersService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*  expense-parent
    28.07.2024
    @author DiachenkoDanylo
*/
@RequestMapping("/logs")
@Controller
public class LogController {

    private final LogService logService;
    private final UsersService usersService;


    public LogController(LogService logService, UsersService usersService) {
        this.logService = logService;
        this.usersService = usersService;
    }

    @GetMapping("/")
    public String showAllUsers(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "10", required = false) Integer perPage,
                               Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {
        List<ClientUserDTO> res = usersService.getAllUsers();
        if (page == null || perPage == null) {
            model.addAttribute("usersList", res);
        } else {
            res =usersService.getAllObjectsWithPagination(page,perPage);
            model.addAllAttributes(usersService.pageAttributes(res,page,perPage));
        }
        model.addAttribute("usersList", res);
        return "log/showAllUsers";
    }

    @GetMapping("/all/")
    public String getAllLogs(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10", required = false) Integer perPage,
                             Model model){
        List<String> logs = logService.getAllLogs();
        if (page == null || perPage == null) {
            model.addAttribute("logsList", logs);
        } else {
            model.addAttribute("logsList", logService.getAllObjectsWithPagination(logs,page,perPage));
            model.addAllAttributes(logService.pageAttributes(logs,page,perPage));
        }
        return "log/allLogs";
    }

    @GetMapping("/user/{userId}")
    public String getUsersLogs(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                               @RequestParam(value = "size", defaultValue = "10", required = false) Integer perPage,
                               Model model, @PathVariable("userId") Integer userId){
        List<String> logs = logService.getLogsByUserId(userId);
        if (page == null || perPage == null) {
            model.addAttribute("logsList", logs);
        } else {
            model.addAttribute("logsList", logService.getAllObjectsWithPagination(logs,page,perPage));
            model.addAllAttributes(logService.pageAttributes(logs,page,perPage));
        }
        model.addAttribute("user",usersService.getUserById(userId));
        return "log/showUser";
    }
}
