package com.arcade.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "auth/login-page";
    }


    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "security/access-denied";
    }

    @GetMapping("/logout-user-successfully")
    public String showLogoutPage() {
        return "auth/logout-page";
    }


}
