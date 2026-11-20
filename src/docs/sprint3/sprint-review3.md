# Sprint 3 — Sprint Review

## 1. Informations générales

- **Projet :** Logi-logiciel
- **Sprint :** 3

## 2. Objectif du sprint

L'objectif de ce sprint était de finaliser les principales fonctionnalités du démineur afin d'obtenir un jeu jouable.

Le travail devait notamment porter sur l'implémentation de la condition de victoire, la gestion de la défaite, l'amélioration des interactions avec le joueur ainsi que l'amélioration de l'interface graphique.

## 3. Présentation des fonctionnalités réalisées

| ID | Fonctionnalité prévue | Résultat obtenu | Statut |
|---|---|---|---|
| 1 | Détection de la victoire | La victoire est détectée lorsque toutes les cases sans mine ont été découvertes. | Validé |
| 2 | Gestion de la défaite | La découverte d'une mine déclenche la défaite et révèle les mines présentes sur le plateau. | Validé |
| 3 | Gestion des interactions | Ajout d'un menu permettant de choisir la taille de la grille et la difficulté. | Validé |
| 4 | Amélioration de l'interface | Création d'un menu principal, amélioration de l'affichage | Validé |
| 5 | Vérification du fonctionnement global | Les principales mécaniques sont intégrées, mais des vérifications globales restent nécessaires. | Validé |
| 6 | Tests unitaires | Plusieurs tests JUnit couvrent les cases, les déplacements, les drapeaux et les mécaniques du jeu. | Partiellement validé |
| 7 | Chronomètre | Ajout d'un chronomètre qui démarre lors de la première découverte d'une case. | Validé |
| 8 | Compteur de drapeaux | Ajout d'un compteur indiquant le nombre de drapeaux placés par rapport au nombre de mines. | Validé |

## 4. Éléments non terminés ou non validés

- L'affichage de la victoire reste à améliorer afin de proposer un écran de fin cohérent avec celui de la défaite.
- Le chronomètre doit être arrêté correctement à la fin d'une partie.
- Les tests doivent être exécutés et complétés pour vérifier le fonctionnement global du jeu.
- Les classes dédiées aux bombes ne sont pas encore exploitées pour proposer de nouvelles mécaniques de gameplay.
- Le jeu ne dispose pas encore d'une progression par niveaux ni d'un mode campagne.
- Aucune ambiance sonore n'est actuellement intégrée.

## 5. Bilan du sprint

Le Sprint 3 a permis de faire évoluer considérablement le projet.

Le jeu dispose maintenant d'un menu principal, d'un système de sélection de difficulté, d'un chronomètre et d'un compteur de drapeaux. Les conditions de victoire et de défaite sont également présentes dans le code, ce qui représente une avancée importante par rapport aux deux premiers sprints.

L'ajout de l'animation d'explosion améliore également l'expérience visuelle lors d'une défaite.

Cependant, le jeu reste encore assez limité dans ses mécaniques et dans sa progression. Il manque notamment des éléments permettant de renouveler l'expérience de jeu et de proposer un véritable parcours au joueur.

Le Sprint 3 constitue donc une étape importante dans la réalisation du projet : les bases du jeu sont désormais présentes et le prochain sprint pourra se concentrer sur l'enrichissement du gameplay, l'immersion et la progression.