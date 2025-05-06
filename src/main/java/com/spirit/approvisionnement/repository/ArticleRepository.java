package com.spirit.approvisionnement.repository;

import com.spirit.approvisionnement.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByFournisseurId(Long fournisseurId);
}

