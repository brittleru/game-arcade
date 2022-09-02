package com.arcade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragController {

    @GetMapping("/base-frag")
    public String baseFrag(Model model) {
        return "fragments/base";
    }

}
