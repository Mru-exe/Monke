package monke;
import monke.controllers.LevelLoader;

import monke.models.Level;
import monke.models.entities.Player;

/**
 * Main class for the game.
 * This class initializes the game and starts the main game loop.
 */
public class Game {
    public static void main(String[] args) {
        Level currentLevel = LevelLoader.loadLevel("defaultLevel.json"); //TODO

        //GameWindow window = new GameWindow("Monke Game");
        startGameLoop(currentLevel);
    }

    private static void startGameLoop(Level level) {
        //TODO: open main game window

        level.spawnPlayer(new Player(0, 0, 0, 50, 50));

        //TODO loop
        
    }
}