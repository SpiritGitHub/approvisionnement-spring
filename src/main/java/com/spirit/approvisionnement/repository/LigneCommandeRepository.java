package com.spirit.approvisionnement.repository;

import com.spirit.approvisionnement.model.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {}
