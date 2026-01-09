# Dungeon Game - Jeu au Tour par Tour

## Contributeurs

- [Angèle Denys](mailto:angele.denys@telecomnancy.eu)
- [Paul Musial](mailto:paul.musial@telecomnancy.eu)
- [Elisa Girres](mailto:elisa.girres@telecomnancy.eu)
- [Kevin Bui](mailto:kevin.bui@telecomnancy.eu)

## Installation des Packages

### Prérequis
- **Java 17** ou supérieur
- **Maven** (pour la gestion des dépendances)
- Un navigateur web moderne

### Installation

1. Clonez le dépôt :

1.1. Via SSH :
```bash
git clone git@gibson.telecomnancy.univ-lorraine.fr:projets/2526/codingweek/grp05.git
cd grp05
```

1.2. Via HTTPS :
```bash
git clone https://gibson.telecomnancy.univ-lorraine.fr/projets/2526/codingweek/grp05.git
cd grp05
```

2. Installez les dépendances Maven :
```bash
mvn clean install
```

---

## Lancement en Terminal

Pour lancer le jeu en mode terminal, 

utilisez la commande suivante :
```bash
mvn clean compile exec:java -Dexec.mainClass="dungeon.ui.cli.TerminalLauncher"
```

**OU**  

utilisez le script fourni :
```bash
./cli-game.sh
```

---

## Lancement en Mode Web

### Démarrage du Serveur Backend

Compilez et lancez le serveur Spring Boot :
```bash
mvn spring-boot:run
```

**OU**


```bash
mvn clean compile exec:java -Dexec.mainClass="dungeon.Main"
```

Le serveur démarrera sur `http://localhost:8080`

### Accès à l'Interface Web

Par défaut, le serveur écoute les requêtes sur le port `5500`. Pour une configuration locale, lancer le serveur http à l'adresse suivante:
``` 
127.0.0.1:5500
```

Le chemin vers la page d'accueil est :
```
127.0.0.1:5500/src/frontend/landing_page.html
```
L'adresse complète est donc :
```
http://localhost:5500/src/frontend/landing_page.html
```

---

## Utilisation en Terminal

Une fois le jeu lancé en mode terminal :

1. **Suivez les instructions à l'écran** pour naviguer dans le menu
2. **Créez votre champ de bataille** en sélectionnant les obstacles empéchant les héros d'atteindre le trésor, en respectant le budget alloué
3. **Observez le déplacement des héros** sur la grille

---

## Utilisation en Mode Web

1. **Placez les obstacles** sur la grille en les sélectionnant dans le menu latéral, en respectant le budget alloué
2. **Démarrez la partie** en cliquant sur le bouton "Start Game"
3. **Lancer les tours de jeu** en cliquant sur "Next Turn"
4. **Visualisez les déplacements** des héros en temps réel sur la grille

---

## Structure du Projet

```
grp05/
├── src/
│   ├── backend/        # Code Java Spring Boot
│   └── frontend/       # Interface Web (HTML, CSS, JS) et CLI
├── docs/               # Documentation du projet
├── pom.xml             # Configuration Maven
└── cli-game.sh         # Script de lancement terminal
```

---

## Technologies Utilisées

- **Backend** : Java 17 (et +), Spring Boot, WebSocket
- **Frontend** : HTML, CSS, JavaScript (Vanilla)
- **Build** : Maven
- **Tests** : JUnit

---

## Notes

- Les **sauvegardes** sont stockées dans le dossier `saves/`
- Le **port par défaut** du serveur est `8080`
- Pour la **documentation complète**, consultez le dossier `docs/`

---

## Dépannage

### Le serveur ne démarre pas
- Vérifiez que le port 8080 n'est pas déjà utilisé
- Assurez-vous que Java 17 (et +) est correctement installé : `java -version`

### Problème de connexion frontend-backend
- Vérifiez que le serveur backend est bien démarré
- Contrôlez l'URL de connexion dans `connection.js`

### Erreurs Maven
- Nettoyez le cache : `mvn clean`
- Réinstallez les dépendances : `mvn clean install`
