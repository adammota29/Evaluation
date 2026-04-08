# Evaluation - Rick and Morty Locations (KMP)

Application Kotlin Multiplatform (Android + Desktop) construite autour de la Rick and Morty API.

Le sujet est centré sur un parcours simple et propre:
- afficher une liste de locations
- ouvrir le détail d'une location
- navigation mobile liste -> détail
- mode Desktop master-detail (liste à gauche, détail à droite)

---

## 1) Objectif du projet

Ce projet sert de support d'évaluation pour démontrer:
- une initialisation KMP propre
- une architecture clean et modulaire
- une séparation Presentation / Domain / Data
- un flux UDF/MVI lisible
- une intégration cross-platform pertinente

---

## 2) Plateformes cibles

- Android
- Desktop JVM

---

## 3) Fonctionnalités implémentées

### Mobile (Android)
- écran `LocationList`
- écran `LocationDetail`
- navigation liste -> détail

### Desktop (JVM)
- écran unique master-detail
- liste des locations à gauche
- panneau de détail à droite

### Données
- récupération des locations depuis l'API distante
- persistance locale SQLDelight
- observation réactive depuis la base locale (source de vérité)
- synchronisation réseau -> local au démarrage et au refresh

### Cross-platform
- cas `expect/actual` via un `AudioManager`
- implémentation Android + Desktop pour jouer un son lors de l'ouverture du détail

---

## 4) Architecture

Le projet suit une Clean Architecture pragmatique.

### Presentation
- écrans Compose
- `UiState`, `Intent`, `Effect`
- `ViewModel` pour orchestrer les actions

### Domain
- modèle métier `Location`
- contrat `LocationRepository`
- use cases (`GetLocationsUseCase`, `ObserveLocationByIdUseCase`, `SyncLocationsUseCase`)

### Data
- `RickAndMortyApi` (source distante)
- SQLDelight (source locale)
- `LocationRepositoryImpl` (stratégie de fetch + mapping)

### DI
- Koin pour l'injection
- modules communs + modules spécifiques plateforme

---

## 5) Structure du projet

```text
Evaluation/
├── composeApp/
│   ├── src/
│   │   ├── commonMain/
│   │   │   └── kotlin/com/adam/evaluation/core/
│   │   │       ├── presentation/
│   │   │       ├── domain/
│   │   │       ├── data/
│   │   │       ├── di/
│   │   │       └── audio/
│   │   ├── androidMain/
│   │   ├── jvmMain/
│   │   └── commonTest/
│   └── build.gradle.kts
├── gradle/
├── settings.gradle.kts
└── build.gradle.kts
```

---

## 6) Stack technique

- Kotlin Multiplatform
- Compose Multiplatform
- Ktor Client
- SQLDelight
- Koin
- Kotlin Coroutines / Flow
- Kotlinx Serialization

---

## 7) Lancer le projet

### Prérequis
- JDK 17+ pour Gradle
- Android SDK (pour la cible Android)
- Windows/macOS/Linux (Desktop JVM)

> Le projet contient `org.gradle.java.home` dans `gradle.properties` pour pointer vers un JDK local. Adapter ce chemin selon votre machine si nécessaire.

### Vérifier la JVM utilisée par Gradle

```powershell
cd C:\Users\adamm\AndroidStudioProjects\Evaluation
.\gradlew.bat -version
```

### Compiler Desktop JVM

```powershell
cd C:\Users\adamm\AndroidStudioProjects\Evaluation
.\gradlew.bat :composeApp:compileKotlinJvm
```

### Lancer Desktop JVM

```powershell
cd C:\Users\adamm\AndroidStudioProjects\Evaluation
.\gradlew.bat :composeApp:run
```

### Compiler Android (debug)

```powershell
cd C:\Users\adamm\AndroidStudioProjects\Evaluation
.\gradlew.bat :composeApp:compileDebugKotlinAndroid
```

---

## 8) Flux fonctionnel principal

1. L'utilisateur ouvre l'écran liste.
2. Le `LocationListViewModel` observe la base locale.
3. Un sync réseau alimente la base locale.
4. L'UI se met à jour via `Flow`.
5. Au clic sur une location:
   - émission d'un son via `AudioManager`
   - navigation vers le détail (mobile) ou sélection du panneau détail (desktop)

---

## 9) Qualité et lisibilité

- nommage explicite par couche
- responsabilités séparées
- commentaires ciblés uniquement sur les zones non triviales
- dépendances centralisées dans `libs.versions.toml`

---

## 10) Limites actuelles / axes d'amélioration

- renforcer les tests unitaires (Domain + Data)
- enrichir la gestion d'erreurs réseau
- finaliser la documentation des choix cross-native (notamment extension `Context` Android)
- optionnel: pagination et amélioration UX

---

## 11) Auteur

- Projet réalisé par Adam dans le cadre de l'évaluation du module Kotlin Multiplatform.

