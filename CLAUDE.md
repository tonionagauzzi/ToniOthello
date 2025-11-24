# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

ToniOthello is an Othello game built with Kotlin Multiplatform and Compose Multiplatform, targeting Android, iOS, Web (JS and Wasm), and Desktop (JVM).

## Architecture

### Multiplatform Structure

This project follows the Kotlin Multiplatform source set structure:

- `composeApp/src/commonMain/kotlin`: Shared business logic and UI components for all platforms
- `composeApp/src/androidMain/kotlin`: Android-specific implementations and entry points
- `composeApp/src/iosMain/kotlin`: iOS-specific implementations (framework exported to iosApp)
- `composeApp/src/jvmMain/kotlin`: Desktop (JVM) specific implementations
- `composeApp/src/jsMain/kotlin`: JavaScript specific implementations for web
- `composeApp/src/wasmJsMain/kotlin`: WebAssembly specific implementations for web
- `composeApp/src/webMain/kotlin`: Shared web code (JS + Wasm)
- `composeApp/src/commonTest/kotlin`: Shared test code

### Platform Entry Points

- **Android**: `MainActivity.kt` in androidMain
- **iOS**: `MainViewController.kt` in iosMain (exports framework to iosApp)
- **Desktop**: `main.kt` in jvmMain (mainClass: `com.vitantonio.nagauzzi.toniothello.MainKt`)
- **Web**: `main.kt` in webMain

### Expect/Actual Pattern

Platform-specific implementations use the expect/actual mechanism:
- `Platform.kt` in commonMain declares expected platform APIs
- `Platform.android.kt`, `Platform.ios.kt`, etc. provide actual implementations

## Build Commands

### Run Tests
```bash
./gradlew test
```

### Run Tests for Specific Target
```bash
./gradlew :composeApp:androidTest          # Android unit tests
./gradlew :composeApp:jvmTest              # JVM tests
./gradlew :composeApp:iosSimulatorArm64Test # iOS simulator tests
```

### Android
```bash
./gradlew :composeApp:assembleDebug        # Build debug APK
./gradlew :composeApp:assembleRelease      # Build release APK
./gradlew :composeApp:installDebug         # Install debug APK on connected device
```

### Desktop (JVM)
```bash
./gradlew :composeApp:run                  # Run desktop app
./gradlew :composeApp:packageDmg           # Package as macOS DMG
./gradlew :composeApp:packageMsi           # Package as Windows MSI
./gradlew :composeApp:packageDeb           # Package as Linux DEB
```

### Web
```bash
# WebAssembly (faster, modern browsers)
./gradlew :composeApp:wasmJsBrowserDevelopmentRun    # Run dev server
./gradlew :composeApp:wasmJsBrowserProductionRun     # Run production build

# JavaScript (slower, supports older browsers)
./gradlew :composeApp:jsBrowserDevelopmentRun        # Run dev server
./gradlew :composeApp:jsBrowserProductionRun         # Run production build
```

### iOS
For iOS, open the `iosApp` directory in Xcode and run from there. The Kotlin code is compiled to a framework that's linked to the SwiftUI app.

### Build All Targets
```bash
./gradlew build
```

## Development Notes

### Package Structure
Base package: `com.vitantonio.nagauzzi.toniothello`

### Target JVM Version
- Android: JVM 11 (compileSdk from version catalog)
- Desktop: JVM 11

### Compose Resources
Resources are managed via Compose Multiplatform resources in `composeApp/src/commonMain/composeResources/`. Access them using the generated `Res` object.

### Version Catalog
Dependencies and versions are managed in `gradle/libs.versions.toml` (accessed via `libs` prefix in build scripts).

### Hot Reload
This project includes the Compose Hot Reload plugin for faster development iteration.