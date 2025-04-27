package monke.controllers;

import monke.models.GameLevel;

/**
 * LevelLoader is responsible for loading levels from JSON files.
 * It deserializes the level data and returns a Level object.
 */
public class LevelLoader {

    /**
     * Loads a level from a external file.
     * @param levelFilePath
     * @return Level object of the level
     */
    public static GameLevel loadLevel(String levelFilePath) {
        //TODO: deserialize 

        return new GameLevel("", 0, 0);
    }
}
