package com.spirit.approvisionnement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private CommandeApprovisionnement commande;


    @ManyToOne
    private Article article;

    private Integer quantite;

    private Double prixUnitaire;
}
