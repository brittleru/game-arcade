package com.arcade.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GamesController {

    @GetMapping("/list")
    public String getGamesList(Model model) {
        List<String> gamesList = new ArrayList<>();
        gamesList.add("Nier: Automata");
        gamesList.add("Nier: Replicant");
        gamesList.add("Lost Ark");
        gamesList.add("Bayonetta");
        gamesList.add("Rocket Science");

        model.addAttribute("games", gamesList);

        return "games/list-games";
    }

    @GetMapping("/rocket-science")
    public String getRocketScience() {
        return "builds/rocket/index";
    }

    @GetMapping("/snake")
    public String getSnake() {
        return "games/js/snake";
    }
}
