# projet-logilogiciel

## 1. Présentation du projet

Démineur-Labyrinthe: un plateau de 15*25 avec des bombes “classiques” de démineur, ainsi que des mines particulières que le personnage doit déminer. Lors du déminage, une zone d’explosion apparaît avec un temps donné pour fuir de cette zone au risque d’exploser et de perdre. De plus, il a aussi le risque de perdre avec les bombes classiques. Le personnage a aussi la mission d’aller d’un point A (avec une mini zone sans bombes) à un point B pour finir la partie. 

L'application est développée en Java et utilise Maven pour automatiser la compilation, l'exécution des tests et la génération du fichier JAR.

## 2. Prérequis

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

## 3. Récupération du projet

Cloner le dépôt Git :

```bash
git clone https://github.com/Elfyfy/projet-logilogiciel
```

Se placer ensuite dans le dossier du projet :

```bash
cd projet-logilogiciel
```

## 4. Compilation

Pour compiler le projet :

```bash
mvn clean compile
```

Cette commande supprime les fichiers de compilation précédents, puis compile les sources Java.

## 5. Exécution des tests

Pour compiler et exécuter les tests unitaires :

```bash
mvn test
```

Les tests sont écrits avec JUnit Jupiter.

## 6. Exécution de l'application

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

## 7. Structure du projet

```text
projet-logilogiciel/
├── pom.xml
├── README.md
├── docs/
│   ├── sprint-1/
│   └── sprint-2/
└── src/
    ├── main/
    │   └── java/
    └── test/
        └── java/
```

- `pom.xml` : configuration Maven et dépendances.
- `src/main/java/` : code source de l'application.
- `src/test/java/` : tests unitaires.
- `docs/` : backlogs, diagrammes UML et comptes rendus des sprints.
- `README.md` : documentation principale du projet.

## 8. Équipe

- OROZCO Julien
- CRANSAC-LORINEAU Nolan
- CLAUSS Jules
- GUERGUIL Maxence
- CHENU Mathis
