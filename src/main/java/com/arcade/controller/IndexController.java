package com.arcade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("date", new java.util.Date());
        return "index";
    }

}
