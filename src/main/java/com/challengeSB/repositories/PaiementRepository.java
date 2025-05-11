package com.challengeSB.repositories;

import com.challengeSB.model.Etudiant;
import com.challengeSB.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Integer> {
    List<Paiement> findByEtudiant(Etudiant etudiant);
}
