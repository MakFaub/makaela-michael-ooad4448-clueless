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
The project emphasizes clean architecture, extensibility, and core software engineering principles.

Players navigate a mansion, gather clues, and must deduce:

-  **Suspect** 👤
-  **Weapon** 🗡️
-  **Room** 🏠

  Official Rules:  
  https://www.hasbro.com/common/instruct/clueins.pdf

Due to project scope and time constraints, this implementation includes a modified rule set and a custom-designed map.

### TBD
 

## Core Classes

| Category   | Classes                                                 |
|------------|---------------------------------------------------------|
| Game Board | `Map`, `Hallway`, `Room`                                |
| Cards      | `Card`, `Deck`, `RoomCard`, `SuspectCard`, `WeaponCard` |
| Pieces     | `Piece`, ``                                             |
| Artifacts  | `Artifact`, `ArtifactFactory`                           |

---

## Interfaces

| Interface | Purpose |
|----------|--------|
| `Die` | Dice rolling abstraction |
| `CardFactory` | Creates cards |
| `PieceFactory` | Creates suspects & weapons |
| `GameObserver` | Observes game events |

---

## Design Patterns

### Factory Pattern
**Used In:**  ``
-

---

### Builder Pattern
**Used In:** ``
- 

---

### Observer Pattern
**Used In:** ``
- 

---

