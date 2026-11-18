# Sprint 3 — Sprint Retrospective

## 1. Informations générales

- **Projet :** Logi-logiciel
- **Sprint :** 3

## 2. Ce qui s'est bien passé

- Amélioration importante de l'interface graphique avec la création d'un menu principal d'abord implémenté sous forme d'un menu de sélection de la difficulté.
- Mise en place de plusieurs paramètres de jeu, notamment le choix de la taille de la grille et du niveau de difficulté.
- Implémentation de la condition de victoire lorsque toutes les cases sans mine ont été découvertes.
- Mise en place de la gestion de la défaite lorsqu'une mine est découverte.
- Ajout d'une animation d'explosion et d'un écran de fin de partie permettant de recommencer ou de quitter.
- Implémentation d'un chronomètre qui démarre lors de la première découverte d'une case.
- Ajout d'un compteur permettant de suivre le nombre de drapeaux placés.
- Amélioration de la personnalisation des commandes du jeu.
- Poursuite du développement des tests unitaires avec JUnit.

## 3. Ce qui s'est moins bien passé

- Certaines fonctionnalités restent à finaliser, notamment l'affichage et la gestion de l'écran de victoire.
- Le fonctionnement global du jeu nécessite encore des vérifications afin de détecter d'éventuels bugs.
- Certaines classes, notamment celles concernant les bombes, existent mais ne sont pas encore pleinement intégrées aux mécaniques de jeu.
- L'interface graphique pourrait encore être améliorée pour rendre l'expérience plus immersive.

## 4. Axes d'amélioration

- Finaliser la gestion des différents états de fin de partie, notamment la victoire.
- Vérifier et corriger les éventuels problèmes dans les mécaniques de jeu.
- Améliorer l'intégration des différentes classes afin de rendre le code plus cohérent.
- Développer de nouvelles mécaniques pour enrichir le gameplay.
- Améliorer l'immersion sonore et visuelle du jeu.
- Mettre en place un système de progression pour proposer une difficulté évolutive.

## 5. Actions à mettre en place pour le prochain sprint

- Implémenter un nouveau type de mine : les bombes à retardement, qui devront être déclenchées pour permettre au joueur de progresser et de gagner.
- Ajouter des effets sonores et une musique de fond pour le menu principal et les phases de jeu.
- Développer un système de niveaux avec une difficulté progressive.
- Créer un mode campagne permettant d'enchaîner plusieurs niveaux avec des objectifs différents.
- Corriger les éventuels bugs détectés lors des tests.
- Compléter les tests unitaires pour vérifier les nouvelles mécaniques.

## 6. Évaluation du sprint

- **Objectif du sprint :** Globalement atteint.
- **Principale réussite :** Le jeu possède désormais les principales fonctionnalités nécessaires à une partie complète, avec une gestion de la victoire, de la défaite, des difficultés et des interactions.
- **Principale difficulté :** Certaines fonctionnalités restent à finaliser et le gameplay manque encore de diversité et de progression.
- **Priorité d'amélioration pour le prochain sprint :** Enrichir le gameplay avec les bombes à retardement, augementer la difficulté en améliorant la crétion des grilles, ajouter une ambiance sonore et développer un mode campagne avec une progression par niveaux.