package com.spirit.approvisionnement.service;

import com.spirit.approvisionnement.model.Article;
import com.spirit.approvisionnement.model.Fournisseur;
import com.spirit.approvisionnement.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public List<Article> getArticlesByFournisseur(Long fournisseurId) {
        return articleRepository.findByFournisseurId(fournisseurId);
    }

}
