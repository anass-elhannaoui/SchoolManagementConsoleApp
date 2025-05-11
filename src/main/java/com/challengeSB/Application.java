package com.challengeSB;

import com.challengeSB.model.Etudiant;
import com.challengeSB.repositories.EtudiantRepository;
import com.challengeSB.service.EtudiantService;
import com.challengeSB.service.NoteService;
import com.challengeSB.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Scanner scanner = new Scanner(System.in);

	@Autowired
	private EtudiantService etudiantService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private PaiementService paiementService;
	@Autowired
	private EtudiantRepository etudiantRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// pour l'application consolle on utilise run() de commandlineRunner
	@Override
	public void run(String... args) throws Exception {
		boolean running = true;
		while (running) {
			System.out.println("1. Administrateur");
			System.out.println("2. Étudiant");
			System.out.println("3. Pour quitter l'application");
			System.out.print("Choisir une option: ");
			String choice = scanner.nextLine();

			switch (choice) {
				case "1":
					AdminMenu();
					break;
				case "2":
					EtudiantMenu();
					break;
				case "3":
					System.out.println("Exit!");
					running = false;
					break;
				default:
					System.out.println("⚠️ Choisir une option Valide! ");
			}
		}
	}

	public void AdminMenu() {
		while (true) {

			System.out.println("\n       ----- Menu Admin -----");
			System.out.println("1. Ajouter un étudiant");
			System.out.println("2. Saisir les notes d’un étudiant");
			System.out.println("3. Enregistrer un paiement");
			System.out.println("4. Voir la liste des étudiants avec leur solde");
			System.out.println("0. Retour au menu principal");

			System.out.print("Votre choix : ");
			String choix = scanner.nextLine();

			switch (choix) {
				case "1":
					System.out.println("-------------------------------------------");
					etudiantService.ajouterEtudiant();
					System.out.println("-------------------------------------------");
					break;
				case "2":
					System.out.println("-------------------------------------------");
					System.out.println("Saisir les notes:");
					noteService.saisirNote();
					System.out.println("-------------------------------------------");
					break;
				case "3":
					System.out.println("-------------------------------------------");
					System.out.println("Enregistrer un paiement:");
					paiementService.enregistrerPaiement();
					System.out.println("-------------------------------------------");
					break;
				case "4":
					System.out.println("-------------------------------------------");
					System.out.println("Liste des étudiants:");
					etudiantService.afficherEtudiantsAvecSolde();
					break;

				case "0":
					return;
				default:
					System.out.println("⛔ Choix invalide !");
					System.out.println("-------------------------------------------");
			}
		}
	}

	public void EtudiantMenu() {
		//simulation d'auth:
		boolean Athenticated = false;
		Etudiant etudiantDeVerification = null;
		Etudiant etudiantEnligne = null;
		System.out.println("     ----- Connectez Vous ! -----");


		while(!Athenticated){
			System.out.print("Email: ");
			String email = scanner.nextLine();
			System.out.print("Mot de passe: ");
			String motDePass = scanner.nextLine();
			//verification du password:
			//avant de récupérer l'utilisateur à partir du database, il faut verifier qu'il exist, pour eviter NullPointerException
			if(etudiantRepository.existsByEmail(email)){
				etudiantDeVerification = etudiantService.getEtudiantByEmail(email);
				//verifier le password:
				if(!motDePass.equals(etudiantDeVerification.getMotDePasse())){
					System.out.println("⛔ email ou mot de passe incorrect !"); //pour eviter le brute force attack
				}else{
					etudiantEnligne = etudiantDeVerification;
					Athenticated = true;
				}
			}
			else{
				System.out.println("⛔ email ou mot de passe incorrect !");
			}


		}
		while (Athenticated) {
			System.out.println("\n    ---- Bienvenue "+ etudiantEnligne.getNom()+" ----");

			System.out.println("1. Consulter mes notes");
			System.out.println("2. Voir L'historique de mes paiement");
			System.out.println("3. Voir mon solde à payer");
			System.out.println("4. Modifier mon mot de passe");
			System.out.println("0. Déconnexion");
			System.out.print("Votre choix : ");
			String choix = scanner.nextLine();
			String email = etudiantEnligne.getEmail();
			switch (choix) {
				case "1":
					System.out.println("    ------ Vos notes: ------");
					noteService.AfficherNotes(email);
					System.out.println("----------------------------");
					break;
				case "2":
					System.out.println("    ------ Vos paiement: ------");
					paiementService.AfficherHistoryDePaiement(email);
					System.out.println("-------------------------------------------");
					break;
				case "3":
					System.out.println("-------------------------------------------");
					System.out.println("Il vous reste "+ etudiantEnligne.getSoldeRestant()+"DH a payer");
					System.out.println("-------------------------------------------");
					break;
				case "4":
					System.out.println("-------------------------------------------");
					System.out.println("Modifier votre mot de passe:");
					etudiantService.modifierInfos(etudiantEnligne);
					System.out.println("-------------------------------------------");
					break;
				case "0":
					Athenticated = false;
					break;
				default:
					System.out.println("⛔ Choix invalide !");
					System.out.println("-------------------------------------------");
			}
		}
	}
}
