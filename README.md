# makaela-michael-ooad4448-clueless

---

**Names:** Michael Giesecke, Makaela Fauber  
**Java Version:** 23

---

# Mid-Project Review - CSCI 4448/5448

**Project Value:** 10 points

## Requirements: 
- Submitting a URL to your repo is REQUIRED for this assignment, and it will be graded on this rubric:
- At least 3 design patterns are clearly identified with a written explanation of where and how each will be used in the architecture. -- **3 points**
- Foundational classes and interfaces are designed and implemented, with real logic beginning to take shape — not just empty stubs or boilerplate. -- **3 points**
- Code demonstrates core OO principles: coding to abstractions, at least one example of polymorphism, and dependency injection where applicable. -- **2 points**
- At least 5 meaningful test cases have been designed. -- **2 points**

## Overview

**Clueless** is a fully object-oriented implementation of the classic board game *Clue*.

Players navigate a mansion, gather clues, and must deduce:

-  **Suspect** 👤
-  **Weapon** 🗡️
-  **Room** 🏠

  Official Rules:  
  https://www.hasbro.com/common/instruct/clueins.pdf

Due to project scope and time constraints, this implementation includes a modified rule set and a custom map.

### TBD
 

## Core Classes

| Category   | Classes                                                                |
|------------|------------------------------------------------------------------------|
| Game Play  | `GameObserver`, `GameAdapter`                                          |
| Game Board | `Board`, `Space`, `Room`, `Hallway`                                    |
| Cards      | `Card`, `Deck`, `RoomCard`, `SuspectCard`, `WeaponCard`, `CardFactory` |
| Pieces     | `Piece`, `SuspectPiece`, `WeaponPiece`, `PieceFactory`                  |
| Player     | `Player`, `Hand`                                                       |
| Artifacts  | `Artifact`, `ArtifactFactory`                                          |

---

## Interfaces

| Interface | Purpose |
|----------|--------|
| `Die` | Dice rolling abstraction |
| `GameObserver` | Observes game events |

---

## Design Patterns

### Factory Pattern
**Used In:**  `ArtifactFactory`, `CardFactory`, `PieceFactory`

- The Factory design pattern will be used to create new instances of game objects like Artifacts, Cards, and Pieces.

---

### Builder Pattern
**Used In:** `GameAdapter`

- The Builder pattern will be used in the GameAdapter class in order to configure the game setup.
- It will setup the board, players, decks, and starting rooms for all game pieces.

---

### Observer Pattern
**Used In:** `GameObserver`

- The Observer design pattern will be used in GameObserver to decouple UI and logging from state changes.
- GameObserver will keep track of game events, such as turns, suggestions/guesses, accusations, and card reveals via the concealment artifacts.
---

