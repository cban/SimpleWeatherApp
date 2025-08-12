
# Simple Weather App
## Overview
SimpleWeatherApp is a clean and responsive Android application that provides real time weather information based on user inputted city names
Built with Kotlin and Jetpack Compose for UI using the MVVM architecture. The app fetches weather data from a public API (OpenWeatherMap) and displays it in a user friendly interface.

## Architecture
SimpleWeatherApp is follows  the MVVM (Model - View - ViewModel) architecture  with the Repository pattern to promote a clear separation of concerns ,testability and maintability


Due to the simplicity of the app, the app is structured into two distinct layers :

### Data Layer
This layer contains the data models, a repository, and a single network data source. The repository acts as the single source of truth for all data operations.
It handles fetching weather data from the network using Retrofit through the network data source. 
This abstraction shields the rest of the app from network implementation details and makes it easy to extend later with features like caching or local storage.

###  Presentation/UI Layer
#### ViewModel:
 Serves as the central state holder so that the ui state is not changed in the views, exposing UI state as observable StateFlows. It acts as the bridge between the data layer and the UI, handling errors, and controlling the app’s ui logic.

#### View: 
The UI views were built  using Jetpack Compose, the UI observes the ViewModel’s state and reacts declaratively to any changes. User interactions (like button clicks) are sent back to the ViewModel, maintaining a clean, unidirectional data flow.

## Technologies

- **UI**: Jetpack Compose, Material 3
- **State Management**: StateFlow, Kotlin Flow
- **Dependency Injection**: Hilt
- **Networking**: Retrofit, OkHttp
- **Concurrency**: Coroutines
- **Image Loading**: Coil
- **Testing**: JUnit5, MockK
## Minimum Requirements
- Minimum Android SDK version: 24 (Android 7.0 Nougat)

## Getting Started

### Installation
- Clone the repository

- - git clone https://github.com/cban/SimpleWeatherApp.git


- Open the project in Android Studio (preferably the latest stable version).
- Sync Gradle files to ensure all dependencies are downloaded
- Set up API key
 - - Get your free API key from OpenWeatherMap
- Add the key to your local.properties file
- - **apiKey** = ``your_api_key_here``
- - Note: Replace ``your_api_key_here`` with your actual OpenWeatherMap API key

- Build and run
./gradlew installDebug
Or simply click the "Run" button in Android Studio to build and install on your connected device/emulator.
## App Video Link 
[App video Link](https://github.com/cban/SimpleWeatherApp/blob/main/Screen_recording_20250812_135005.mp4)


