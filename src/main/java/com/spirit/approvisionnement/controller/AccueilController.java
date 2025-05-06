package com.spirit.approvisionnement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccueilController {

    @GetMapping("/")
    public String accueil() {
        return "index"; // correspond à src/main/resources/templates/index.html
    }

    @GetMapping("/accueil")
    public String home() {
        return "index";
    }
}

