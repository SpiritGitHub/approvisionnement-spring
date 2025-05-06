package com.spirit.approvisionnement.service;

import com.spirit.approvisionnement.model.LigneCommande;
import com.spirit.approvisionnement.repository.LigneCommandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigneCommandeService {

    private final LigneCommandeRepository ligneCommandeRepository;

    public LigneCommandeService(LigneCommandeRepository ligneCommandeRepository) {
        this.ligneCommandeRepository = ligneCommandeRepository;
    }

    public List<LigneCommande> getAllLignes() {
        return ligneCommandeRepository.findAll();
    }

    public Optional<LigneCommande> getLigneById(Long id) {
        return ligneCommandeRepository.findById(id);
    }

    public LigneCommande saveLigne(LigneCommande ligneCommande) {
        return ligneCommandeRepository.save(ligneCommande);
    }

    public void deleteLigne(Long id) {
        ligneCommandeRepository.deleteById(id);
    }
}
