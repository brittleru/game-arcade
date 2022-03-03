package com.arcade.controller;

import com.arcade.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

@Controller
public class IndexController {

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("date", new java.util.Date());
        return "index";
    }

}
