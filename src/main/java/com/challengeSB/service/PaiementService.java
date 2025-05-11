package com.challengeSB.service;

import com.challengeSB.repositories.PaiementRepository;
import org.springframework.stereotype.Service;
import com.challengeSB.model.Etudiant;
import com.challengeSB.model.Paiement;
import com.challengeSB.repositories.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
            scanner.close();
            return;
        }

        // Lire soldeRestant (avec input Validation)
        double montant = -1;
        boolean valide = false;

        while (!valide) {
            try {

                System.out.print("Montant du paiement : ");


                montant = Double.parseDouble(scanner.nextLine());
                if(montant<0){
                    System.out.println("⚠️ Le paiement doit etre positif!");

                }else if (montant > etudiant.getSoldeRestant() ){
                    System.out.println("⚠️ Le paiement est superieur au solde restant!");
                }else{
                    valide = true;
                }


            } catch (NumberFormatException e) {
                System.out.println("⛔ Entrée invalide. Veuillez saisir un nombre pour le Paiement.");
            }
        }

        Paiement paiement = new Paiement();
        paiement.setEtudiant(etudiant);
        paiement.setMontant(montant);
        paiement.setDate(LocalDateTime.now());

        paiementRepository.save(paiement);

        // mis à jour le solde de l'étudiant
        etudiant.setSoldeRestant(etudiant.getSoldeRestant() - montant);
        etudiantRepository.save(etudiant);

        System.out.println("✅ Paiement enregistré !");
        scanner.close();
    }

    public void AfficherHistoryDePaiement(String email){
        Etudiant etudiant = etudiantRepository.findByEmail(email);
        List<Paiement> paiements = paiementRepository.findByEtudiant(etudiant);
        System.out.printf("%-20s %-10s\n", "Date", "Montant");
        for(Paiement paiement: paiements ){
            // on format la date pour qu'elle soit plus lisible
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String date = paiement.getDate().format(formatter);

            System.out.printf("%-20s %-10.2fDH\n", date, paiement.getMontant());
        }
    }
}
