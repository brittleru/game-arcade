package com.arcade.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/games")
public class GamesController {


    @GetMapping("/list")
    public String getGamesList(Model model) {


        List<String> gamesList = new ArrayList<>();
        gamesList.add("Overwatch");
        gamesList.add("Nier: Automata");
        gamesList.add("Nier: Replicant");
        gamesList.add("Bayonetta");

        model.addAttribute("games", gamesList);

        return "list-games";
    }

}
