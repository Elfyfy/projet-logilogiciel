# Sprint 4 — Backlog de sprint

## 1. Objectif du sprint

Enrichir le gameplay du démineur en introduisant de nouvelles mécaniques et en améliorant l'expérience du joueur.

Ce sprint aura pour objectif d'implémenter des bombes à retardement qui devront être déclenchées pour remporter certains niveaux, d'ajouter une ambiance sonore au menu et aux parties, et de développer un système de progression par niveaux permettant de créer un mode campagne.

Le sprint devra également permettre de corriger les éventuels bugs détectés lors des précédents développements et de consolider les fonctionnalités existantes.

## 2. Backlog

| ID | Fonctionnalité | Tâches à réaliser | Priorité | Responsable | Critères d'acceptation | Statut |
|---|---|---|---|---|---|---|
| 1 | Bombes à retardement | Développer un nouveau type de bombe possédant un compte à rebours avant son explosion. | Haute | À définir | La bombe peut être activée et explose après un délai défini. | À faire |
| 2 | Gestion des explosions | Implémenter les conséquences de l'explosion des bombes et leur interaction avec le joueur et le plateau. | Haute | À définir | L'explosion est correctement déclenchée et ses effets sont visibles en jeu. | À faire |
| 3 | Objectifs liés aux bombes | Ajouter des objectifs nécessitant de déclencher les bombes à retardement pour terminer certains niveaux. | Haute | À définir | Le joueur doit déclencher les bombes nécessaires pour pouvoir gagner. | À faire |
| 4 | Ambiance sonore du menu | Ajouter une musique de fond au menu principal. | Moyenne | À définir | Une musique est jouée lorsque le joueur se trouve dans le menu principal. | À faire |
| 5 | Ambiance sonore en jeu | Ajouter une musique de fond pendant les parties ainsi que des effets sonores pour les actions importantes. | Moyenne | À définir | Les déplacements, explosions et événements importants peuvent être accompagnés de sons. | À faire |
| 6 | Gestion du son | Ajouter un système permettant de régler ou de désactiver le volume des musiques et des effets sonores. | Faible | À définir | Le joueur peut activer, désactiver et régler le volume sonore. | À faire |
| 7 | Système de niveaux | Créer une structure permettant de définir plusieurs niveaux avec des paramètres différents. | Moyenne | À définir | Chaque niveau possède sa propre configuration et ses propres objectifs. | À faire |
| 8 | Progression de difficulté | Faire évoluer progressivement la difficulté des niveaux en modifiant la taille des cartes, le nombre de mines et les bombes présentes. | Moyenne | À définir | Les niveaux deviennent progressivement plus difficiles. | À faire |
| 9 | Mode campagne | Créer un mode de jeu permettant d'enchaîner plusieurs niveaux dans un ordre défini. | Moyenne | À définir | Le joueur peut terminer un niveau et accéder au suivant. | À faire |
| 10 | Gestion de la progression | Enregistrer les niveaux terminés et empêcher l'accès aux niveaux suivants tant que les précédents ne sont pas terminés. | Moyenne | À définir | La progression du joueur est conservée et les niveaux sont débloqués progressivement. | À faire |
| 11 | Interface du mode campagne | Ajouter un menu permettant de sélectionner ou de consulter les niveaux disponibles. | Moyenne | À définir | Le joueur peut visualiser les niveaux débloqués et sa progression. | À faire |
| 12 | Chronomètre | Faire en sorte que le chronomètre s'arrête à la fin d'une partie et que le temps mis pour résoudre le niveau s'affiche | Moyenne | À définir | Affiché si partie gagnée | À faire |
| 13 | Victoire | Écran de victoire affiché en cas de résolution correcte de la grille | Haute | À définir | Si gagne, alors affichage gagné | À faire |
| 14 | Tests unitaires | Ajouter des tests JUnit pour les bombes à retardement, les explosions et la progression des niveaux. | Haute | À définir | Les nouvelles fonctionnalités sont couvertes par des tests qui passent avec `mvn test`. | À faire |

## 3. Définition de « terminé »

Une tâche est considérée comme terminée lorsque :

- Le code correspondant est implémenté.
- Le code compile sans erreur.
- Les tests pertinents sont exécutés et réussis.
- La fonctionnalité est intégrée correctement au reste du jeu.
- Le fonctionnement est vérifié dans l'interface graphique.
- La documentation est mise à jour si nécessaire.

## 4. Estimation des tâches

Les estimations seront définies par l'équipe en fonction de la complexité de chaque fonctionnalité et du temps disponible pour le sprint.

Les tâches les plus importantes concernent les bombes à retardement, le système de niveaux et le mode campagne.

## 5. Dépendances et risques

- Le système de bombes à retardement dépend de l'intégration correcte des classes `Bomb` et `Tile` dans le gameplay.
- Les explosions doivent être compatibles avec les mécaniques existantes de défaite et de victoire.
- Les objectifs liés aux bombes doivent être définis avant la validation de la victoire d'un niveau.
- Le système de progression nécessite une structure permettant de charger différentes configurations de niveaux.
- Le mode campagne dépend du fonctionnement du système de niveaux.
- La gestion du son doit être compatible avec les différents écrans du jeu et ne pas perturber les performances.
- L'ajout de nouvelles mécaniques nécessite de compléter les tests unitaires afin d'éviter les régressions.

## 6. Bilan du backlog

À la fin du sprint, les tâches seront mises à jour selon leur état réel.

Les fonctionnalités réalisées et validées seront recensées dans `sprint-review4.md`.

Les difficultés rencontrées, les points positifs et les améliorations à apporter seront détaillés dans `sprint-retrospective4.md`.