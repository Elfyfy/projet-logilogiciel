# Sprint 3 — Backlog de sprint

## 1. Objectif du sprint

Finaliser les fonctionnalités principales du démineur afin d'obtenir un jeu entièrement jouable, notamment en implémentant la condition de victoire, la gestion de la défaite et les interactions avec les cases.

## 2. Backlog

| ID | Fonctionnalité | Tâches à réaliser | Priorité | Responsable | Critères d'acceptation | Statut |
|---|---|---|---|---|---|---|
| 1 | Détection de la victoire | Implémenter la condition de victoire lorsque toutes les cases sans bombe sont révélées. | Haute | Maxence | La victoire est détectée lorsque toutes les cases nécessaires sont révélées. | À faire |
| 2 | Gestion de la défaite | Détecter lorsqu'une bombe est révélée et terminer la partie. | Haute | Jules | La partie se termine lorsqu'une bombe est découverte. | À faire |
| 3 | Gestion des interactions | Ajouter la possibilité de placer et retirer des drapeaux sur les cases suspectées de contenir une bombe. | Moyenne | Nolan | Le joueur peut placer et retirer un drapeau sur une case cachée. | À faire |
| 4 | Amélioration de l'interface | Ajouter des indications visuelles pour la victoire et la défaite et améliorer la lisibilité du jeu. | Moyenne | Nolan / Julien | Les différents états de la partie sont clairement visibles. | À faire |
| 5 | Vérification du fonctionnement global | Tester les différentes mécaniques du jeu et corriger les éventuels bugs. | Haute | Mathis | Les fonctionnalités principales fonctionnent correctement ensemble. | À faire |
| 6 | Tests unitaires | Compléter les tests JUnit pour vérifier les conditions de victoire, de défaite et la gestion des drapeaux. | Moyenne | Mathis | Les tests passent avec `mvn test`. | À faire |
| 7 | Chronomètre | Créer un chronomètre pour suivre le temps au cours d'une game. | Faible | Mathis | Le chrnomètre se lance au premier clic et s'arrête lorsqu'une mine est découverte. | À faire |
| 8 | Compteur de drapeaux | Créer un compteur pour suivre le nombre de drapeaux placés. | Faible | Mathis | Le nombre de drapeaux augmente dès que le joueur place un drapeau. | À faire |