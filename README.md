# MemeVerse - Professional Meme Generator

MemeVerse is a modern, high-performance Android application designed for creating professional-grade memes with ease. It combines a smooth, Canva-like UI with powerful local image processing capabilities.

## 🚀 Features

### 1. Home Screen
- **Trending Memes**: Stay updated with the latest meme trends.
- **Recent Projects**: Quick access to your ongoing work.
- **Creation Hub**: Dedicated buttons for Image, GIF, and Sticker memes.
- **Dark Mode**: Full support for a premium night-time experience.

### 2. Tenor GIF Integration
- Access millions of GIFs directly through the **Tenor GIF API v2**.
- Smooth scrolling grid layout for browsing.
- Real-time GIF search.

### 3. Professional Meme Editor
- **Multi-Layer Editing**: Add and manage multiple images, GIFs, stickers, and text layers.
- **Gesture Control**: Intuitive drag, resize (zoom), and rotate functionality for every layer.
- **History System**: Robust Undo/Redo support to ensure a non-destructive editing experience.
- **Local Processing**: All editing is done on-device for speed and privacy.

### 4. Advanced Tools
- **Text Editor**: Comprehensive font styles, colors, shadows, and outlines.
- **Drawing Tool**: Freehand drawing with various brushes and markers.
- **Background Remover**: Manual erase/restore brushes for transparent PNG export.
- **Image Editing**: Pro tools for cropping, brightness, contrast, and filters.

### 5. Export & Share
- High-definition (HD) export in PNG, JPG, and GIF formats.
- One-tap sharing to WhatsApp, Instagram, X (Twitter), and Telegram.

---

## 🛠 Tech Stack

- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) - For a modern, reactive UI.
- **Architecture**: MVVM + Clean Architecture.
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/) - For scalable and testable code.
- **Networking**: [Retrofit](https://square.github.io/retrofit/) + [Gson](https://github.com/google/gson) - For Tenor API integration.
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) - Optimized for Bitmaps and GIFs.
- **Local Storage**: [Room Database](https://developer.android.com/training/data-storage/room) - For project persistence.
- **Concurrency**: Kotlin Coroutines & Flow.

---

## 📂 Project Structure

```text
com.example.mememaker/
├── data/               # Data Layer: Repositories, API, and Local DB
├── domain/             # Domain Layer: UseCases and Business Models
├── ui/                 # Presentation Layer: Compose Screens, Themes, and Navigation
│   ├── components/     # Reusable UI elements
│   ├── screens/        # Feature-specific screens (Home, Editor, GIF)
│   └── navigation/     # NavHost and Route definitions
└── util/               # Helper classes and Image processing extensions
```

---

## 🛠 Installation & Setup

1. Clone the repository: `git clone https://github.com/your-repo/MemeMaker.git`
2. Open the project in **Android Studio**.
3. Sync the Gradle project.
4. Add your **Tenor API Key** in `MemeRepositoryImpl.kt` (or build config).
5. Run the app on an emulator or physical device.

---

## 💎 Premium Strategy
- **Premium Assets**: Exclusive stickers and templates.
- **Clean Experience**: Ad-free editing and watermark removal for premium users.
- **Monetization**: Integrated with AdMob (Banner, Interstitial, and Reward ads).

## ✅ MVP Checklist
- [x] Full App Architecture (MVVM + Hilt)
- [x] Folder Structure setup
- [x] Database Structure (Room)
- [x] Tenor API Integration (GIF & Stickers)
- [x] Multi-layer Editor skeleton (Drag, Scale, Rotate)
- [x] Image picking from Gallery
- [x] Basic Drawing support
- [x] Dark Mode support
- [x] AdMob Initialization

---

## 📜 License
*Project created as a professional Meme Generator portfolio piece.*
