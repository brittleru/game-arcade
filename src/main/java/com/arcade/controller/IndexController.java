package com.arcade.controller;

import com.arcade.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

@Controller
public class IndexController {

    private final static Logger logger = Logger.getLogger(IndexController.class.getName());
//    private UserService userService;
//
//    @Autowired
//    public IndexController(UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("date", new java.util.Date());
        return "index";
    }

}
