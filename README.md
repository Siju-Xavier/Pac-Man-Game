# Pac-Man Game

A classic Pac-Man clone built in Java using Swing/AWT for rendering and game logic.

<img width="908" height="1000" alt="image" src="https://github.com/user-attachments/assets/8b6b1d4a-60b2-4a98-91e3-6b4b43d5a651" />


## Overview

A simple Pac-Man clone built as a learning project. It recreates the core gameplay loop: moving Pac-Man through a maze, avoiding ghosts, and collecting pellets.

## Features

- Grid-based maze navigation with wall collision
- Directional Pac-Man sprite animation (up, down, left, right)
- Four ghosts with distinct sprites (red, pink, orange, blue)
- Power pellets that trigger a "scared" ghost state
- Bonus fruit collectibles (cherries)
- Custom sprite assets for all game entities

## Tech Stack

- **Language:** Java
- **Graphics:** Java Swing / AWT

## Project Structure

```
Pac-Man-Game/
├── App.java          # Entry point, launches the game window
├── PacMan.java        # Core game logic, rendering, and game loop
├── pacmanUp.png
├── pacmanDown.png
├── pacmanLeft.png
├── pacmanRight.png
├── redGhost.png
├── pinkGhost.png
├── orangeGhost.png
├── blueGhost.png
├── scaredGhost.png
├── wall.png
├── cherry.png
├── cherry2.png
├── powerFood.png
└── README.md
```

## Getting Started

### Prerequisites

- Java JDK installed (JDK 8 or later recommended)

### Run Locally

```bash
git clone https://github.com/Siju-Xavier/Pac-Man-Game.git
cd Pac-Man-Game
javac *.java
java App
```

## Notes

Built as a simple exercise in Java game development using Swing/AWT, without any external game engine.
