package com.spirit.approvisionnement.controller;

import com.spirit.approvisionnement.model.Article;
import com.spirit.approvisionnement.service.ArticleService;
import com.spirit.approvisionnement.service.FournisseurService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final FournisseurService fournisseurService;

    public ArticleController(ArticleService articleService, FournisseurService fournisseurService) {
        this.articleService = articleService;
        this.fournisseurService = fournisseurService;
    }

    @GetMapping
    public String listArticles(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        return "article/list";
    }

    @GetMapping("/nouveau")
    public String showForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("fournisseurs", fournisseurService.getAllFournisseurs()); // Vous devez implémenter cette méthode
        return "article/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        articleService.getArticleById(id).ifPresent(a -> {
            model.addAttribute("article", a);
            model.addAttribute("fournisseurs", fournisseurService.getAllFournisseurs());
        });
        return "article/form";
    }

    @PostMapping("/enregistrer")
    public String save(@ModelAttribute Article article) {
        articleService.saveArticle(article);
        return "redirect:/articles";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return "redirect:/articles";
    }
}
