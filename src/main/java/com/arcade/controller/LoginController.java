package com.arcade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLogin() {
        return "auth/login-page";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "security/access-denied";
    }


}
