package com.spirit.approvisionnement.controller;

import com.spirit.approvisionnement.model.Fournisseur;
import com.spirit.approvisionnement.service.FournisseurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fournisseurs")
public class FournisseurController {

    private final FournisseurService fournisseurService;

    public FournisseurController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("fournisseurs", fournisseurService.getAllFournisseurs());
        return "fournisseur/list";
    }

    @GetMapping("/nouveau")
    public String create(Model model) {
        model.addAttribute("fournisseur", new Fournisseur());
        return "fournisseur/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Fournisseur fournisseur) {
        fournisseurService.saveFournisseur(fournisseur);
        return "redirect:/fournisseurs";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        fournisseurService.getFournisseurById(id).ifPresent(f -> model.addAttribute("templates/fournisseur", f));
        return "fournisseur/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        fournisseurService.deleteFournisseur(id);
        return "redirect:/fournisseurs";
    }
}
