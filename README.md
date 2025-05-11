# SchoolManagementConsoleApp
Application console de gestion des étudiants, de leurs notes et des paiements, développée avec Spring Boot.

# Comment utiliser l'application

## Menu Principal
Lorsque vous lancez l'application, vous avez trois choix principaux :

- **Administrateur** : Gérer les étudiants, les notes et les paiements.
- **Étudiant** : Consulter vos informations, vos notes et votre solde.
- **Quitter** : Fermer l'application.

## En Mode Administrateur
L'administrateur peut :

- **Ajouter un étudiant** : Enregistrer un nouvel étudiant.
- **Saisir les notes d'un étudiant** : Ajouter des notes pour les étudiants.
- **Enregistrer un paiement** : Enregistrer les paiements effectués par les étudiants.
- **Voir la liste des étudiants avec leur solde restant** : Consulter les informations de paiement des étudiants.

## En Mode Étudiant
L'étudiant peut :

- **Consulter ses notes** : Voir ses notes pour chaque matière.
- **Voir son solde à payer** : Vérifier le solde restant à régler.
- **Voir L'historique de ses paiement**: Vérifier les dates et les montants de ses paiement.
- **Modifier ses informations** : Mettre à jour ses informations personnelles, dans ce cas c'est le mot de passe.

## Base de données
L'application utilise **H2 Database** en mémoire pour stocker les données des étudiants, de leurs notes et de leurs paiements. Aucune configuration externe n'est nécessaire.

