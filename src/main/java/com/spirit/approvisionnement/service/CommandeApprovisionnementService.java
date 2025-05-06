package com.spirit.approvisionnement.service;

import com.spirit.approvisionnement.model.CommandeApprovisionnement;
import com.spirit.approvisionnement.repository.CommandeApprovisionnementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeApprovisionnementService {

    private final CommandeApprovisionnementRepository commandeRepo;

    public CommandeApprovisionnementService(CommandeApprovisionnementRepository commandeRepo) {
        this.commandeRepo = commandeRepo;
    }

    public List<CommandeApprovisionnement> getAllCommandes() {
        return commandeRepo.findAll();
    }

    public Optional<CommandeApprovisionnement> getCommandeById(Long id) {
        return commandeRepo.findById(id);
    }

    public CommandeApprovisionnement saveCommande(CommandeApprovisionnement commande) {
        commande.setDateCommande(LocalDate.now());
        return commandeRepo.save(commande);
    }
    public void deleteCommande(Long id) {
        commandeRepo.deleteById(id);
    }
}
