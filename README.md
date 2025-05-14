# MonkeFoolery

### Project Overview

MonkeFoolery is a 2D platformer game inspired by the original 1981 Donkey Kong. Built with Java & OpenGL using Maven.

---

## Features
- **Custom Level System**: Define levels using external `json` files.
- **Player Controls**:
    - Move left/right: `A/D` or `Arrow` keys.
    - Jump: `Space` or `Arrow Up`.
    - Players' movement speed (`move_speed`) and jump strength (`jump_strength`) are configurable in the level file.

- **Monkey AI & Barrels**: 
    - Monkey spawn barrels based on a configurable spawn rate (`monkey_cooldown`).
    - Every **third** barrel is **special**—It spawns with a **2,5x** speed.
    - Barrels have fixed initial speed, gradually increasing in speed while rolling on the ground.
    - Barrels are affected by gravity and will reset their velocity to initial value upon hitting the ground.
    - Additionally, barrels have a **30%** chance to change a direction when hitting the ground.

- **Goal Keys**: 
    - Collect keys to unlock the exit door.
    - The number of keys required is defined in the level file.
    - There can be more keys than required, but the player must collect at least the required amount to unlock the door.
  
- **Void & Platforms**:
    - Platform is the surface that entities can stand on. The rest of the map is considered as void.
    - Platforms are defined in the level file as an array format (for example): 
    ```json 
    [{"x": 350.0, "y": 123.4, "width": 400.5, "height": 201.2}, {}]
    ```
    - Entities can only collide with platforms, not with void. 
    - Entities whose XY coordinates exceed the renderable area and will be destroyed.

## Excluded

- The engine does *not* validate whether a level is solvable.