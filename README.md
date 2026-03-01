# 📱 Android Offline-First Client

A sample Android application demonstrating an offline-first architecture using Kotlin, Jetpack Compose, Room, and Retrofit.

This project showcases how to build a scalable mobile client that remains functional in unreliable network environments—similar to real-world safety or IoT monitoring apps.

## ✨ Features

- 📡 Fetch data from a generic remote API (Retrofit + Coroutines); API contract is isolated via DTO → domain mapping
- 💾 Cache data locally using Room (Single Source of Truth)
- 📶 Works offline with previously synced data
- 🔄 Pull-to-refresh to force network synchronization
- 📄 Incremental loading (pagination) with smooth scrolling
- 🧭 Clean separation between UI, domain logic, and data layers

## 🏗️ Architecture Overview

The project follows a Clean Architecture–inspired structure:

```
UI (Jetpack Compose)
        ↓
ViewModel (state holder + paging control)
        ↓
UseCase (business logic abstraction)
        ↓
Repository (data orchestration layer)
        ↓
Local: Room Database   ←→   Remote: Retrofit API
```

### Key Principles

- **Single Source of Truth** → Local database owns the state
- **Offline-first design** → UI reads from cache, network updates DB
- **Unidirectional data flow** → predictable state handling
- **Generic API + DTO → Domain mapping** → remote schema is isolated from UI; swap the endpoint without touching domain or UI

## 📦 Tech Stack

| Layer        | Technology                   |
| ------------ | ---------------------------- |
| UI           | Jetpack Compose (Material3)  |
| State        | ViewModel + StateFlow        |
| Async        | Kotlin Coroutines            |
| Networking   | Retrofit + OkHttp            |
| Persistence  | Room (SQLite)                |
| Architecture | Repository + UseCase pattern |

## 🔄 Data Flow

The remote layer uses a **generic demo API** (replaceable); the architecture is the focus.

### Normal Launch

- App loads cached incidents from Room
- UI renders immediately
- User can refresh to sync latest data from network

### Pull-to-Refresh

Network → Repository → Room → UI

### Pagination

Room query (LIMIT/OFFSET) → incremental UI updates

## 🧪 Why This Project Exists

This project is intentionally focused on architecture and data flow, not UI complexity.

It demonstrates patterns commonly required in production mobile apps:

- Handling unreliable connectivity
- Decoupling API contracts from business models
- Managing incremental loading efficiently
- Designing maintainable code boundaries

## 🚀 Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/Linda5823/android-offline-first-client.git
   ```

2. Open in Android Studio（Hedgehog (2023.1.1) 或 Ladybug (2024.2) 及以上）
3. Run on an emulator or device (API 24+)

No additional configuration required.

## 📌 Future Extensions (Not Implemented by Design)

- Dependency Injection (e.g., Hilt)
- Replace generic demo API with a real incident backend (and server-side pagination)
- Image loading pipeline
- Background sync workers

These were intentionally omitted to keep the example focused and readable.

## 👤 Author

Built as an architecture-focused learning project to explore scalable Android patterns.
