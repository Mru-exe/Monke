package monke.controllers;

import monke.models.Level;

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
    public static Level loadLevel(String levelFilePath) {
        //TODO: deserialize 

        return new Level("", 0, 0);
    }
}
