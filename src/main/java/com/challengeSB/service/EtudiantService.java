package com.challengeSB.service;

import com.challengeSB.model.Etudiant;
import com.challengeSB.repositories.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;
    public void ajouterEtudiant() {
        Scanner scanner = new Scanner(System.in);

        Etudiant etudiant = new Etudiant();

        System.out.println("Saisir les données de l'étudiant :");

        // Demander les informations de l'étudiant
        System.out.print("Nom: ");
        String nom = scanner.nextLine();

        // on verifier l'unicite de l'email:
        String email = "";
        boolean valide = false;
        while(!valide){
           // on verifier l'unicite de l'email:
           System.out.print("Email: ");
           email = scanner.nextLine();
           // pour un projet reel, on ajoute une validation d'email la.
           if (etudiantRepository.existsByEmail(email)) {
               System.out.println("⛔ Cet email est déjà utilisé.");
           }else{
               valide = true;
           }
       }
        System.out.print("Mot de passe: ");
        String motDePasse = scanner.nextLine();

        System.out.print("Classe: ");
        String classe = scanner.nextLine();

        // Lire soldeRestant (avec input Validation)
        double soldeRestant = 0;
        valide = false;

        while (!valide) {
            try {
                System.out.print("Solde restant: ");
                soldeRestant = Double.parseDouble(scanner.nextLine());
                if(soldeRestant<0){
                    System.out.println("⚠️ Le solde restant doit etre positif!");

                }else{
                    valide = true;
                }


            } catch (NumberFormatException e) {
                System.out.println("⛔ Entrée invalide. Veuillez saisir un nombre pour le solde restant.");
            }
        }

        etudiant.setNom(nom);
        etudiant.setEmail(email);
        etudiant.setMotDePasse(motDePasse);
        etudiant.setClasse(classe);
        etudiant.setSoldeRestant(soldeRestant);

        etudiantRepository.save(etudiant);
        System.out.println("✅ L'étudiant a été ajouté avec succès !");
        scanner.close();
    }


    public void afficherEtudiantsAvecSolde() {
        List<Etudiant> etudiants = etudiantRepository.findAll();
        System.out.printf("%-5s %-10s %-20s %-15s %-10s %-10s\n", "ID", "Nom", "Email", "Mot de passe", "Classe", "Solde restant");
        System.out.println("---------------------------------------------------------------------------------");

        for (Etudiant etudiant : etudiants) {
            System.out.printf("%-5d %-10s %-20s %-15s %-10s %.2fDH\n",
                    etudiant.getId(),
                    etudiant.getNom(),
                    etudiant.getEmail(),
                    etudiant.getMotDePasse(),
                    etudiant.getClasse(),
                    etudiant.getSoldeRestant()
            );
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    public Etudiant getEtudiantByEmail(String email) {
        return etudiantRepository.findByEmail(email);
    }

    public void modifierInfos(Etudiant etudiant) {
        Etudiant e = etudiantRepository.findByEmail(etudiant.getEmail());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("🔑 Nouveau mot de passe : ");
            String nouveauMotDePasse = scanner.nextLine();
            System.out.print("🔁 Confirmer le mot de passe : ");
            String confirmation = scanner.nextLine();
            //password validation
            if (nouveauMotDePasse.isBlank() || confirmation.isBlank()) {
                System.out.println("⚠️ Le mot de passe ne peut pas être vide.");
                continue;
            }

            if (nouveauMotDePasse.equals(confirmation)) {
                e.setMotDePasse(nouveauMotDePasse);
                etudiantRepository.save(e);
                System.out.println("✅ Mot de passe modifié avec succès !");
                break;
            } else {
                System.out.println("❌ Les mots de passe ne correspondent pas. Veuillez réessayer.");
            }
            // on peut aussi ajouter des contraint pour assurer que le mot de passe est fort quand necessaire
        }
        scanner.close();    
    }



}
