package com.propelize.vehicleapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthWebController {

    @GetMapping({"/", "/login"})
    public String login() {
        return "index";
    }
}
