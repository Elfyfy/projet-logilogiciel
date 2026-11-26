# Sprint 4 — Backlog de sprint

## 1. Objectif du sprint

Enrichir le gameplay du démineur en introduisant de nouvelles mécaniques et en améliorant l'expérience du joueur.

Ce sprint aura pour objectif d'implémenter des bombes à retardement qui devront être déclenchées pour remporter certains niveaux, d'ajouter une ambiance sonore au menu et aux parties, et de développer un système de progression par niveaux permettant de créer un mode campagne.

Le sprint devra également permettre de corriger les éventuels bugs détectés lors des précédents développements et de consolider les fonctionnalités existantes.

## 2. Backlog

| ID | Fonctionnalité | Tâches à réaliser | Priorité | Responsable | Critères d'acceptation | Statut |
|---|---|---|---|---|---|---|
| 1 | Bombes à retardement et explosions | Développer un nouveau type de bombe avec compte à rebours, puis implémenter l'explosion et ses conséquences sur le joueur et le plateau. | Haute | Maxence | La bombe peut être activée et explose après un délai défini, avec des conséquences sur le plateau et sur le joueur. | En cours |
| 2 | Système de niveaux et progression de difficulté | Créer une structure définissant plusieurs niveaux (configuration et objectifs propres) dont la difficulté augmente (taille de carte, nombre de mines, bombes présentes). | Moyenne | Nolan | Chaque niveau possède sa configuration et ses objectifs, et les niveaux deviennent progressivement plus difficiles. | En cours |
| 3 | Progression et sélection des niveaux | Enregistrer les niveaux terminés, bloquer l'accès aux niveaux suivants tant que les précédents ne sont pas finis, et ajouter un menu pour consulter et choisir les niveaux. | Moyenne | Nolan | La progression est conservée, les niveaux se débloquent progressivement et le joueur voit les niveaux disponibles. | À faire |
| 4 | Ambiance sonore | Ajouter une musique de fond (menu et parties), des effets sonores (déplacements, explosions, événements importants) et un réglage ou une coupure du volume. | Moyenne | Jules | Musique et sons sont joués dans le menu et en jeu, et le joueur peut régler ou désactiver le volume. | À faire |
| 5 | Façon de creuser la map | Définir et implémenter la manière dont le joueur creuse la map et révèle les cases. | Haute | Maxence | Le joueur peut creuser une case et celle-ci se révèle selon les règles du démineur. | Maxence |
| 6 | Apparence graphique | Améliorer l'apparence du personnage et des drapeaux, créer l'apparence des bombes et ajouter un moyen de montrer l'explosion des bombes. | Moyenne | Julien | Le personnage, les drapeaux et les bombes ont un visuel distinct et lisible, et l'explosion d'une bombe est visible à l'écran. | En cours |
| 7 | Tests unitaires | Ajouter des tests JUnit pour les bombes à retardement, les explosions et la progression des niveaux. | Haute | Mathis | Les nouvelles fonctionnalités sont couvertes par des tests qui passent avec `mvn test`. | En cours |

## 3. Définition de « terminé »

Une tâche est considérée comme terminée lorsque :

- Le code correspondant est implémenté.
- Le code compile sans erreur.
- Les tests pertinents sont exécutés et réussis.
- La fonctionnalité est intégrée correctement au reste du jeu.
- Le fonctionnement est vérifié dans l'interface graphique.

## 4. Estimation des tâches

Les estimations seront définies par l'équipe en fonction de la complexité de chaque fonctionnalité et du temps disponible pour le sprint.

Les tâches les plus importantes concernent les bombes à retardement et le système de niveaux.

## 5. Dépendances et risques

- Le système de bombes à retardement dépend de l'intégration correcte des classes `Bomb` / `CaseBombe` et `Tile` / `Case` dans le gameplay. Deux hiérarchies coexistent (`Bomb extends Tile` et `CaseBombe extends Case`) : il faut choisir laquelle est utilisée en jeu.
- Les explosions doivent être compatibles avec les mécaniques existantes de défaite et de victoire.
- Le système de progression nécessite une structure permettant de charger différentes configurations de niveaux (aujourd'hui codées en dur dans `Main`).
- L'apparence des bombes et de leur explosion dépend de la réalisation des bombes à retardement.
- La gestion du son doit être compatible avec les différents écrans du jeu et ne pas perturber les performances.
- L'ajout de nouvelles mécaniques nécessite de compléter les tests unitaires afin d'éviter les régressions.

## 6. Bilan du backlog

À la fin du sprint, les tâches seront mises à jour selon leur état réel.

Les fonctionnalités réalisées et validées seront recensées dans `sprint-review4.md`.

Les difficultés rencontrées, les points positifs et les améliorations à apporter seront détaillés dans `sprint-retrospective4.md`.
