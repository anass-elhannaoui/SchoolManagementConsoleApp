package com.challengeSB.service;

import com.challengeSB.repositories.PaiementRepository;
import org.springframework.stereotype.Service;
import com.challengeSB.model.Etudiant;
import com.challengeSB.model.Paiement;
import com.challengeSB.repositories.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Scanner;

@Service
public class PaiementService {
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private PaiementRepository paiementRepository;

    public void enregistrerPaiement() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Email de l'étudiant : ");
        String email = scanner.nextLine();

        Etudiant etudiant = etudiantRepository.findByEmail(email);
        if (etudiant == null) {
            System.out.println("⛔ Étudiant non trouvé !");
            return;
        }

        System.out.print("Montant du paiement : ");
        double montant = scanner.nextDouble();

        Paiement paiement = new Paiement();
        paiement.setEtudiant(etudiant);
        paiement.setMontant(montant);
        paiement.setDate(LocalDateTime.now());

        paiementRepository.save(paiement);

        // Mettre à jour le solde de l'étudiant
        etudiant.setSoldeRestant(etudiant.getSoldeRestant() - montant);
        etudiantRepository.save(etudiant);

        System.out.println("✅ Paiement enregistré !");
    }
}
