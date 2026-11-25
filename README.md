# projet-logilogiciel

## 1. Présentation du projet

**Démineur-Bombeur** : un jeu de démineur dans lequel le joueur doit **passer de niveau en niveau**. Chaque niveau se joue sur un plateau (de 10×15 à 20×35 cases, 15×25 par défaut) contenant des mines “classiques” de démineur, ainsi que des **bombes à retardement** : une fois déclenchée, une bombe explose après un compte à rebours et crée une zone d'explosion que le personnage doit fuir à temps.

Le joueur perd s'il découvre une mine ou s'il est pris dans une explosion. Il gagne le niveau en découvrant toutes les cases sûres (et, selon les niveaux, en déclenchant les bombes requises). La difficulté augmente progressivement d'un niveau à l'autre : taille de la carte, nombre de mines et bombes présentes.

Le chronomètre mesure le temps mis pour résoudre chaque niveau et s'affiche à la victoire.

L'application est développée en Java et utilise Maven pour automatiser la compilation, l'exécution des tests et la génération du fichier JAR.


## 2. Commandes du jeu

Commandes par défaut :

| Touche | Action |
|---|---|
| `Z` | Se déplacer vers le haut |
| `Q` | Se déplacer vers la gauche |
| `S` | Se déplacer vers le bas |
| `D` | Se déplacer vers la droite |
| `F` | Placer ou retirer un drapeau sur une case |
| `espace` | Creuser une case |
| `R` | Rejouer (une fois la partie terminée) |
| `échap` | Revenir au menu |

### Modifier les commandes

Les touches de déplacement, de drapeau et de creusage sont **personnalisables** : depuis le menu principal, ouvrir **PARAMÈTRES**, sélectionner l'action à modifier puis appuyer sur la nouvelle touche (les flèches directionnelles sont utilisables). Si la touche choisie est déjà utilisée par une autre action, les deux actions échangent leurs touches. Le bouton **RÉINITIALISER** rétablit les commandes par défaut.

Les touches `R` (rejouer) et `échap` (menu) ne sont pas modifiables.


## 3. Prérequis

Les outils suivants sont nécessaires pour compiler et exécuter le projet :

- **JDK :** Java 21
- **Maven :** Apache Maven 3.8.7
- **JUnit Jupiter :** 5.12.2, pour les tests unitaires

Vérifier les installations avec les commandes suivantes :

```bash
java -version
javac -version
mvn -version
```

## 4. Récupération du projet

Cloner le dépôt Git :

```bash
git clone https://github.com/Elfyfy/projet-logilogiciel
```

Se placer ensuite dans le dossier du projet :

```bash
cd projet-logilogiciel
```

## 5. Compilation

Pour compiler le projet :

```bash
mvn clean compile
```

Cette commande supprime les fichiers de compilation précédents, puis compile les sources Java.

## 6. Exécution des tests

Pour compiler et exécuter les tests unitaires :

```bash
mvn test
```

Les tests sont écrits avec JUnit Jupiter. Ils couvrent la quasi-totalité des classes du modèle (cases, tuiles, bombes, personnage, chronomètre, commandes, thèmes, effets visuels, jeu, contrôleur et rendu) ainsi que le menu principal.

## 7. Exécution de l'application

### Méthode 1 : avec Maven

```bash
mvn compile exec:java
```

### Méthode 2 : avec le fichier JAR

Générer le fichier JAR :

```bash
mvn clean package
```

Exécuter ensuite l'application :

```bash
java -jar target/ProjetJava-1.0-SNAPSHOT.jar
```

## 8. Structure actuelle du projet

```text
projet-logilogiciel/
├── pom.xml
├── README.md
│
├── docs/
│   ├── sprint1/
│   │   ├── diagramme-classes.png
│   │   ├── backlog1.md
│   │   ├── sprint-retrospective1.md
│   │   └── sprint-review1.md
│   │
│   ├── sprint2/
│   │   ├── backlog2.md
│   │   ├── sprint-retrospective2.md
│   │   └── sprint-review2.md
│   │
│   ├── sprint3/
│   │   ├── backlog3.md
│   │   ├── sprint-retrospective3.md
│   │   └── sprint-review3.md
│   │
│   └── sprint4/                        (sprint en cours : review et rétrospective à venir en fin de sprint)
│       ├── diagramme-classes.png
│       └── backlog4.md
│
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── engine/
    │   │   │   ├── Cmd.java
    │   │   │   ├── DrawingPanel.java
    │   │   │   ├── Game.java
    │   │   │   ├── GameController.java
    │   │   │   ├── GameEngineGraphical.java
    │   │   │   ├── GamePainter.java
    │   │   │   └── GraphicalInterface.java
    │   │   │
    │   │   ├── model/
    │   │   │   ├── Bomb.java
    │   │   │   ├── Case.java
    │   │   │   ├── CaseLibre.java
    │   │   │   ├── CaseMinee.java
    │   │   │   ├── Character.java
    │   │   │   ├── Chronometre.java
    │   │   │   ├── Commandes.java
    │   │   │   ├── Confettis.java
    │   │   │   ├── DemineurController.java
    │   │   │   ├── DemineurGame.java
    │   │   │   ├── DemineurPainter.java
    │   │   │   ├── Empty.java
    │   │   │   ├── Flammes.java
    │   │   │   ├── Mine.java
    │   │   │   ├── Sprite.java
    │   │   │   ├── Theme.java
    │   │   │   ├── Tile.java
    │   │   │   └── Wall.java
    │   │   │
    │   │   ├── start/
    │   │   │   └── Main.java
    │   │   │
    │   │   └── view/
    │   │       └── MenuPrincipal.java
    │   │
    │   └── resources/
    │       └── images/
    │           ├── flag.png
    │           ├── gilet.png
    │           ├── lgbt2.png
    │           ├── menu_fond.png
    │           └── mine.png
    │
    └── test/
        └── java/
            ├── model/
            │   ├── BombTest.java
            │   ├── CaseLibreTest.java
            │   ├── CaseMineeTest.java
            │   ├── CaseTest.java
            │   ├── CharacterTest.java
            │   ├── ChronometreTest.java
            │   ├── CommandesTest.java
            │   ├── ConfettisTest.java
            │   ├── DemineurControllerTest.java
            │   ├── DemineurGameTest.java
            │   ├── DemineurPainterTest.java
            │   ├── EmptyTest.java
            │   ├── FlammesTest.java
            │   ├── MineTest.java
            │   ├── SpriteTest.java
            │   ├── ThemeTest.java
            │   ├── TileTest.java
            │   └── WallTest.java
            │
            └── view/
                └── MenuPrincipalTest.java
```


### Rôle des principaux dossiers

- src/main/java/engine/ : moteur et infrastructure du jeu, gestion des commandes, du contrôleur, de l'affichage et de l'interface graphique.
- src/main/java/model/ : éléments du modèle et règles du jeu (cases, tuiles, mines, bombes, personnage, chronomètre, commandes configurables, thèmes, effets visuels, plateau et rendu associé).
- src/main/java/view/ : écrans et menus de l'application.
- src/main/java/start/ : point d'entrée de l'application (Main.java).
- docs/ : documentation du projet organisée par sprint : backlogs, rétrospectives, reviews et documents de conception (diagrammes de classes).
- src/main/resources/images/ : ressources graphiques utilisées par l'application.
- src/test/java/model/ : tests unitaires des classes du modèle et des composants associés.
- src/test/java/view/ : tests unitaires des écrans et menus.
- pom.xml : configuration Maven du projet et de ses dépendances.


## 9. Équipe

- OROZCO Julien
- CRANSAC-LORINEAU Nolan
- CLAUSS Jules
- GUERGUIL Maxence
- CHENU Mathis
