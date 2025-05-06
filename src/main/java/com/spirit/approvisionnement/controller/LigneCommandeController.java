package com.spirit.approvisionnement.controller;

import com.spirit.approvisionnement.model.LigneCommande;
import com.spirit.approvisionnement.service.LigneCommandeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lignes")
public class LigneCommandeController {

    private final LigneCommandeService ligneCommandeService;

    public LigneCommandeController(LigneCommandeService ligneCommandeService) {
        this.ligneCommandeService = ligneCommandeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("lignes", ligneCommandeService.getAllLignes());
        return "ligne/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("ligne", new LigneCommande());
        return "ligne/form";
    }

    @PostMapping
    public String save(@ModelAttribute LigneCommande ligne) {
        ligneCommandeService.saveLigne(ligne);
        return "redirect:/lignes";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        ligneCommandeService.getLigneById(id).ifPresent(l -> model.addAttribute("ligne", l));
        return "ligne/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        ligneCommandeService.deleteLigne(id);
        return "redirect:/lignes";
    }
}
