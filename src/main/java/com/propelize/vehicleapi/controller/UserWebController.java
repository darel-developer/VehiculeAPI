package com.propelize.vehicleapi.controller;

import com.propelize.vehicleapi.model.User;
import com.propelize.vehicleapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserWebController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "users/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name, @RequestParam String password, Model model) {
        try {
            User user = new User();
            user.setName(name);
            user.setPassword(password);
            userService.saveUser(user);
            model.addAttribute("success", "Compte créé avec succès. Connectez-vous !");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "users/register";
    }
}
