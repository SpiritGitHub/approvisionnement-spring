package com.spirit.approvisionnement.controller;

import com.spirit.approvisionnement.model.*;
import com.spirit.approvisionnement.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/commandes")
public class CommandeApprovisionnementController {

    private final CommandeApprovisionnementService commandeService;
    private final FournisseurService fournisseurService;
    private final ArticleService articleService;

    public CommandeApprovisionnementController(CommandeApprovisionnementService commandeService,
                                               FournisseurService fournisseurService,
                                               ArticleService articleService) {
        this.commandeService = commandeService;
        this.fournisseurService = fournisseurService;
        this.articleService = articleService;
    }

    @GetMapping
    public String listCommandes(Model model) {
        model.addAttribute("commandes", commandeService.getAllCommandes());
        return "commande/list";
    }

    /*@GetMapping("/nouveau")
    public String showForm(Model model) {
        model.addAttribute("commande", new CommandeApprovisionnement());
        model.addAttribute("fournisseurs", fournisseurService.getAllFournisseurs());
        return "commande/form";
    }*/

    @GetMapping("/nouveau")
    public String nouvelleCommande(@RequestParam(value = "fournisseurId", required = false) Long fournisseurId, Model model) {
        CommandeApprovisionnement commande = new CommandeApprovisionnement();
        model.addAttribute("commande", commande);
        model.addAttribute("fournisseurs", fournisseurService.getAllFournisseurs());
        model.addAttribute("fournisseurId", fournisseurId);

        if (fournisseurId != null) {
            // Récupérer le fournisseur et ses articles
            Fournisseur fournisseur = fournisseurService.getFournisseurById(fournisseurId).orElse(null);
            if (fournisseur != null) {
                model.addAttribute("articles", articleService.getArticlesByFournisseur(fournisseurId));
                commande.setFournisseur(fournisseur);
            }
        }

        return "commande/form";
    }


    @PostMapping("/enregistrer")
    public String saveCommande(@ModelAttribute CommandeApprovisionnement commande,
                               @RequestParam("articleIds") List<Long> articleIds,
                               @RequestParam("quantites") List<Integer> quantites,
                               @RequestParam("prixUnitaires") List<Double> prixUnitaires,
                               @RequestParam(value = "dateLivraison", required = false) String dateLivraison,
                               @RequestParam(value = "factureFile", required = false) MultipartFile factureFile) throws IOException {

        List<LigneCommande> lignes = new ArrayList<>();
        for (int i = 0; i < articleIds.size(); i++) {
            Article article = articleService.getArticleById(articleIds.get(i)).orElse(null);
            if (article != null) {
                lignes.add(LigneCommande.builder()
                        .article(article)
                        .quantite(quantites.get(i))
                        .prixUnitaire(prixUnitaires.get(i))
                        .commande(commande)
                        .build());
            }
        }

        commande.setLignes(lignes);

        if ("LIVREE".equals(commande.getStatut())) {
            if (dateLivraison != null) {
                commande.setDateLivraison(LocalDate.parse(dateLivraison));
            }
            if (factureFile != null && !factureFile.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + factureFile.getOriginalFilename();
                Path path = Paths.get("factures", fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, factureFile.getBytes());
                commande.setFacturePath(path.toString());
            }
        }

        commandeService.saveCommande(commande);
        return "redirect:/commandes";
    }

    @GetMapping("/edit/{id}")
    public String editCommande(@PathVariable Long id, Model model) {
        CommandeApprovisionnement commande = commandeService.getCommandeById(id).orElseThrow();
        model.addAttribute("commande", commande);
        model.addAttribute("fournisseurs", fournisseurService.getAllFournisseurs());
        model.addAttribute("articles", articleService.getArticlesByFournisseur(commande.getFournisseur().getId()));
        return "commande/modifier"; // 🔁 Affiche la vue modifier.html
    }


}
