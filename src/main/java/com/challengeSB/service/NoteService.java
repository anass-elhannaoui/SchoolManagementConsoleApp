package com.challengeSB.service;

import com.challengeSB.model.Etudiant;
import com.challengeSB.model.Note;
import com.challengeSB.repositories.EtudiantRepository;
import com.challengeSB.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class NoteService {
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private NoteRepository noteRepository;

    public void saisirNote() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Email de l'étudiant: ");
        String email = scanner.nextLine();

        Etudiant etudiant = etudiantRepository.findByEmail(email);
        if (etudiant == null) {
            System.out.println("⛔ Étudiant non trouvé !");
            return;
        }

        System.out.print("Matière: ");
        String matiere = scanner.nextLine();


        // input validation pour la note (un flaot entre 0 et 20):
        boolean valide = false;
        double val = -1;
        while(!valide){
            System.out.print("Note (valeur) : ");
            String valeur = scanner.nextLine();
            try{
                val = Double.parseDouble(valeur);
                if(val<0 ||val>20){
                    System.out.println("⚠️la note doit etre compris entre 0 et 20 !");
                }
                else{
                    break;
                }
            }catch (Exception e){
                System.out.println("⚠️la note doit etre un nombre compris entre 0 et 20 !");
            }

        }
        Note note = new Note();
        note.setEtudiant(etudiant);
        note.setMatiere(matiere);
        note.setValeur(val);

        noteRepository.save(note);
        System.out.println("✅ Note enregistrée !");
    }

    public void AfficherNotes(String email){
        Etudiant etudiant = etudiantRepository.findByEmail(email);
        List<Note> Notes = noteRepository.findByEtudiant(etudiant);
        System.out.printf("%-15s %-10s \n", "Matiere", "Note");
        System.out.println("---------------------");

        for (Note note : Notes) {
            System.out.printf("%-15s %-10.2f\n",
                    note.getMatiere(),
                    note.getValeur()

            );
        }

    }

}
