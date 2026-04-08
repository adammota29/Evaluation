# Evaluation

## Vue d'ensemble

Ce projet suit une approche **Kotlin Multiplatform + Compose** avec une architecture cible en **Clean Architecture**.
L'objectif est d'avoir une base claire, maintenable et testable pour faire évoluer l'application sur plusieurs plateformes.

## Structure du projet

```text
Evaluation/
├── composeApp/
│   ├── src/
│   │   ├── commonMain/   # Code partagé (UI Compose, logique commune)
│   │   ├── androidMain/  # Spécifique Android
│   │   ├── jvmMain/      # Spécifique JVM/Desktop
│   │   └── commonTest/   # Tests partagés
│   └── build.gradle.kts
├── gradle/
├── settings.gradle.kts
└── build.gradle.kts
```

### Modules

- `composeApp` : module principal de l'application (multiplateforme).
- `commonMain` : coeur partagé entre plateformes.
- `androidMain` : implémentations et configuration Android.
- `jvmMain` : implémentations et configuration JVM/Desktop.

## Choix d'architecture : Clean Architecture

L'architecture visée sépare l'application en couches avec des responsabilités claires :

- **Presentation** : écrans, état UI, ViewModels, orchestration d'actions utilisateur.
- **Domain** : règles métier pures (Use Cases, entités, interfaces de repository).
- **Data** : implémentations concrètes (API, base locale, DTO, mappers, repository impl).

### Principes retenus

- Les dépendances vont **de l'extérieur vers l'intérieur**.
- Le **Domain** ne dépend d'aucun framework UI ou infrastructure.
- La **Presentation** dépend du Domain.
- La **Data** implémente les contrats définis dans le Domain.

## Schéma des communications (cible)

```mermaid
flowchart LR
    UI[UI Compose / Screen] --> VM[ViewModel]
    VM --> UC[Use Case\n(Domain)]
    UC --> R[Repository Interface\n(Domain)]
    R --> RI[Repository Implementation\n(Data)]
    RI --> DS[Data Sources\n(API / DB / Cache)]

    DS --> RI
    RI --> R
    R --> UC
    UC --> VM
    VM --> UI
```

## Organisation cible des packages (exemple)

```text
composeApp/src/commonMain/kotlin/.../
├── presentation/
│   ├── screen/
│   ├── component/
│   └── viewmodel/
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
└── data/
    ├── remote/
    ├── local/
    ├── mapper/
    └── repository/
```

## Prochaines étapes

- Poser la convention de nommage des packages et dossiers.
- Créer le premier flux complet `Screen -> ViewModel -> UseCase -> Repository`.
- Ajouter des tests unitaires sur les Use Cases (domain).

