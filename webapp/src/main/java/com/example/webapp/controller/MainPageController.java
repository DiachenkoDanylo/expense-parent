package com.example.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*  expense-parent
    07.06.2024
    @author DiachenkoDanylo
*/
@RequestMapping("/")
@Controller
public class MainPageController {

    @GetMapping("")
    public String indexPage(){
        return "index";
    }
}
