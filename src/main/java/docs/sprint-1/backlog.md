# Sprint 1 — Backlog de sprint

## 1. Objectif du sprint

Mettre en place la structure du projet Java et développer les premières fonctionnalités du jeu de démineur.

## 2. Backlog

| ID | Fonctionnalité | Tâches à réaliser | Priorité | Responsable | Critères d'acceptation | Statut |
|---|---|---|---|---|---|---|
| 1 | Lancer le jeu | Créer la classe principale `Main` et le point d'entrée du programme. | Haute | Julien | Le programme démarre sans erreur. | À faire |
| 2 | Visualiser la map | Concevoir la représentation du démineur et son affichage. | Haute | Nolan | La grille est affiché correctement. | À faire |
| 3 | Se déplacer dans le labyrinthe | Implémenter le déplacement du joueur et les contrôles de collision. | Haute | Jules | Le joueur se déplace sans traverser les murs. | À faire |
| 4 | Gagner | Détecter la condition de victoire et la fin de partie. | Moyenne | Maxence | La victoire est détectée lorsque le joueur gagne. | À faire |
| 5 | Compiler le projet avec Maven | Configurer le `pom.xml` et vérifier la compilation. | Haute | Mathis | `mvn clean compile` fonctionne. | À faire |
| 6 | Vérifier le fonctionnement du jeu | Écrire des tests unitaires avec JUnit. | Moyenne | Julien | Les tests passent avec `mvn test`. | À faire |

## 3. Définition de « terminé »

Une tâche est considérée comme terminée lorsque :

- Le code correspondant est implémenté.
- Le code compile sans erreur.
- Les tests pertinents sont exécutés et réussis.
- Le code est intégré à la branche de travail de l'équipe.
- La documentation est mise à jour si nécessaire.

## 4. Estimation des tâches

| ID | Estimation (minutes) |
|---|---:|
| 1 | 10 |
| 2 | 30 |
| 3 | 30 |
| 4 | 45 |
| 5 | 20 |
| 6 | 20 |

## 5. Dépendances et risques

- La représentation du labyrinthe doit être définie avant l'implémentation des déplacements.
- La détection des collisions dépend de la structure des cases et des murs.
- Les tests nécessitent une structure de code suffisamment modulaire.

## 6. Bilan du backlog

À la fin du sprint, les tâches seront mises à jour selon leur état réel. Les fonctionnalités réalisées et validées seront recensées dans `sprint-review.md`. Les difficultés et les améliorations à apporter seront détaillées dans `sprint-retrospective.md`.