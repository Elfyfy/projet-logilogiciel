# projet-logilogiciel

## 1. Présentation du projet

Démineur-Labyrinthe: un plateau de 15*25 avec des bombes “classiques” de démineur, ainsi que des mines particulières que le personnage doit déminer. Lors du déminage, une zone d’explosion apparaît avec un temps donné pour fuir de cette zone au risque d’exploser et de perdre. De plus, il a aussi le risque de perdre avec les bombes classiques. Le personnage a aussi la mission d’aller d’un point A (avec une mini zone sans bombes) à un point B pour finir la partie. 

L'application est développée en Java et utilise Maven pour automatiser la compilation, l'exécution des tests et la génération du fichier JAR.


## 2. Commandes du jeu

| Touche | Action |
|---|---|
| `Z` | Se déplacer vers le haut |
| `Q` | Se déplacer vers la gauche |
| `S` | Se déplacer vers le bas |
| `D` | Se déplacer vers la droite |
| `F` | Placer ou retirer un drapeau sur une case |
| `espace` | Creuser une case |
| `échap` | Quitter |


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

Les tests sont écrits avec JUnit Jupiter.

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

projet-logilogiciel/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── docs/
    │   │   │   ├── sprint-1/
    │   │   │   │   ├── conception/
    │   │   │   │   │   ├── diagramme-classes.png
    │   │   │   │   │   └── diagramme-sequence.puml
    │   │   │   │   ├── backlog1.md
    │   │   │   │   ├── sprint-retrospective1.md
    │   │   │   │   └── sprint-review1.md
    │   │   │   ├── sprint-2/
    │   │   │   │   ├── backlog2.md
    │   │   │   │   ├── sprint-retrospective2.md
    │   │   │   │   └── sprint-review2.md
    │   │   │   └── sprint-3/
    │   │   │       └── backlog3.md
    │   │   ├── engine/
    │   │   │   ├── Cmd.java
    │   │   │   ├── DrawingPanel.java
    │   │   │   ├── Game.java
    │   │   │   ├── GameController.java
    │   │   │   ├── GameEngineGraphical.java
    │   │   │   ├── GamePainter.java
    │   │   │   └── GraphicalInterface.java
    │   │   ├── model/
    │   │   │   ├── Bomb.java
    │   │   │   ├── Case.java
    │   │   │   ├── CaseLibre.java
    │   │   │   ├── CaseMinee.java
    │   │   │   ├── Character.java
    │   │   │   ├── Chronometre.java
    │   │   │   ├── Commandes.java
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
    │   │   ├── start/
    │   │   │   └── Main.java
    │   │   └── view/
    │   │       ├── MenuDifficulte.java
    │   │       └── MenuPrincipal.java
    │   └── resources/
    │       └── images/
    │           ├── flag.png
    │           ├── gilet.png
    │           ├── lgbt2.png
    │           ├── menu_fond.png
    │           └── mine.png
    └── test/
        └── java/
            └── model/
                ├── CaseLibreTest.java
                ├── CaseMineeTest.java
                ├── CaseTest.java
                ├── ChronometreTest.java
                ├── DemineurControllerTest.java
                ├── DemineurGameTest.java
                └── DemineurPainterTest.java

### Rôle des principaux dossiers

- src/main/java/engine/ : moteur et infrastructure du jeu, gestion des commandes, du contrôleur, de l'affichage et de l'interface graphique.
- src/main/java/model/ : éléments du modèle et règles du jeu (cases, mines, bombes, personnage, chronomètre, plateau et rendu associé).
- src/main/java/view/ : écrans et menus de l'application.
- src/main/java/start/ : point d'entrée de l'application (Main.java).
- src/main/java/docs/ : documentation du projet organisée par sprint : backlogs, rétrospectives, reviews et documents de conception.
- src/main/resources/images/ : ressources graphiques utilisées par l'application.
- src/test/java/model/ : tests unitaires des classes du modèle et des composants associés.
- pom.xml : configuration Maven du projet et de ses dépendances.


## 9. Équipe

- OROZCO Julien
- CRANSAC-LORINEAU Nolan
- CLAUSS Jules
- GUERGUIL Maxence
- CHENU Mathis
