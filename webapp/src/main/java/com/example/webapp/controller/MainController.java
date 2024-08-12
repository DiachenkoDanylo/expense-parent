package com.example.webapp.controller;
/*  expense-parent
    01.06.2024
    @author DiachenkoDanylo
*/

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
@Controller
public class MainController {

    @GetMapping()
    public String index(){
        return "index";
    }


}
