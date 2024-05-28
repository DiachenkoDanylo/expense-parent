package com.example.service.controller;

import com.example.service.ResponseApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/*  expense-parent
    25.05.2024
    @author DiachenkoDanylo
*/
@RestController
@RequestMapping("/api/v1")
public class HiController {

    @GetMapping("")
    public ResponseApi response (Principal principal) {
        if (principal!= null)
            return new ResponseApi( principal.getName());
        return new ResponseApi("nothing to share");
    }
}
