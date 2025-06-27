# Music-Compose-App

Welcome to **MyComposeApp**! This is an Android application built using the Jetpack Compose UI framework. Jetpack Compose simplifies and accelerates UI development on Android by using a modern, declarative approach.

## Sample Video

https://github.com/user-attachments/assets/e52e36ac-3013-4c4e-a4da-f8945e57ff5b

## Features

### 1. Home Screen

- **Top 10 Billboard Songs**: 
  - Displays a dynamic list of the top 10 Billboard songs globally. 
  - Features an intuitive and visually appealing layout using Jetpack Compose.

- **Latest Albums and Songs**: 
  - Showcases a selection of the newest albums and songs.
  - Allows users to quickly browse and discover fresh music releases.
  - Utilizes a grid or list layout for easy navigation through album covers and song titles.
  
 <img width="357" alt="Screenshot 2024-08-20 at 10 32 22 PM" src="https://github.com/user-attachments/assets/bd854ba6-f0ee-4452-8672-58f4eb734f8e">

### 2. Album Detail Page

- **Detailed Album View**:
  - Provides an in-depth look at a selected album, including a complete list of its songs.
  - Features a collapsing header that integrates seamlessly with the scrolling behavior of the song list.
  - Implements zoom-in functionality for the album cover image as the user scrolls through the song list.

- **Custom Animations**:
  - Includes smooth animations when transitioning from the Home Screen to the Album Detail Page.
  - Ensures a visually engaging experience with animated transitions and interactions.

<img width="359" alt="Screenshot 2024-08-20 at 10 54 16 PM" src="https://github.com/user-attachments/assets/0139fbc5-3349-41c8-9256-f1a5461e7135">

### 3. Floating Player

- **Persistent Playback Controls**:
  - A floating player that remains visible across all screens, allowing users to manage music playback easily.
  - Includes basic controls such as play, pause, and skip, as well as indicators for the currently playing song.
  - Designed to be non-intrusive yet accessible, enhancing the user experience without interrupting their interaction with other parts of the app.

 <img width="370" alt="Screenshot 2024-08-20 at 10 53 32 PM" src="https://github.com/user-attachments/assets/933239c2-a8c2-4e17-8e1f-52ac1414af6f">

### 4. Player Detail Bottom Sheet

- **Enhanced Playback Controls**:
  - Provides a detailed bottom sheet with advanced controls for managing song playback.
  - Features options like shuffle, repeat, and playlist management.
  - Displays additional information about the current song, including album art, song duration, and artist details.

- **Interactive Elements**:
  - Incorporates interactive elements such as sliders for adjusting playback position and volume.
  - Allows users to quickly access and control playback settings without leaving their current screen.

  <img width="362" alt="Screenshot 2024-08-20 at 10 54 38 PM" src="https://github.com/user-attachments/assets/6179e075-ba1a-457a-9679-33fb22085ac9">

## Additional Features

- **Custom Composables**:
  - **Album Card**: A reusable composable for displaying album information. It includes custom styles and a shared animation that triggers when the user navigates to the Album Detail Page.
  - **Dynamic Layouts**: Utilizes Jetpack Compose’s flexible layout system to create responsive and adaptive UI components.

- **Smooth Transitions**:
  - Leverages **Compose Animation** to create fluid animations for screen transitions and interactions.
  - Includes shared element transitions to maintain visual continuity when navigating between the Home Screen and Album Detail Page.
## Technologies Used

- **Programming Language**: Kotlin
- **Frameworks**:
  - **Jetpack Compose**: For building the UI.
  - **Room Database**: For local data storage.
  - **Media3**: For media playback.
  - **Compose Animation**: For smooth animations and transitions.
  - **Compose Navigation**: For navigating between screens.
  - **Material3**: For modern design components and themes.

## Architecture

- **Model-View-Intent (MVI)**: Applied for managing UI state and ensuring a clear separation of concerns.





Code is in dev branch.

