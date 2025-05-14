package monke.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import monke.MonkeyGame;
import monke.models.GameLevel;
import monke.models.Goal;
import monke.models.Platform;
import monke.models.base.GameEntity;
import monke.models.entities.GoalKey;
import monke.models.entities.Monkey;
import monke.models.entities.Player;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

/**
 * LevelLoader is responsible for loading levels from JSON files.
 * It deserializes the level data and returns a Level object.
 */
public class LevelLoader {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger logger = Logger.getLogger(LevelLoader.class.getName());

    /**
     * Loads a level from an external file.
     * @param levelFilePath - Path to the level file
     * @return Level object of the level
     */
    public static GameLevel loadLevel(String levelFilePath) throws NullPointerException {
        String json;
        try(InputStream stream = MonkeyGame.class.getClassLoader().getResourceAsStream(levelFilePath)) {
            json = new String(stream.readAllBytes());
        } catch (Exception e){
            logger.severe(e.getMessage());
            return null;
        }
        JsonLevel level = gson.fromJson(json, JsonLevel.class);
        return convertToPlayable(level);
    }

    /**
     * Converts the loaded JSON level to a GameLevel instance.
     * @param level The deserialized JSON level
     * @return GameLevel instance
     */
    private static GameLevel convertToPlayable(JsonLevel level) {
        GameLevel l = new GameLevel();

        GameLevel.moveSpeed = level.moveSpeed;
        GameLevel.jumpStrength = level.jumpStrength;
        GameEntity.gravityStrength = level.gravityStrength;

        l.setPlayer(
                //Default hitbox size is 10x10
                new Player(level.player.x, level.player.y)
        );
        l.setGoal(
                new Goal(level.goal.x, level.goal.y, level.goal.width, level.goal.height, level.keys.size())
        );
        l.setMonkey(
                new Monkey(level.monkey.x, level.monkey.y, level.monkeyCooldown)
        );
        l.setPlatforms(
                new CopyOnWriteArraySet<>(Arrays.asList(level.platforms.stream()
                        .map(p -> new Platform(p.x, p.y, p.width, p.height))
                        .toArray(Platform[]::new)))
        );
        l.setGoalKeys(
                new CopyOnWriteArraySet<>(Arrays.asList(level.keys.stream()
                        .map(i -> new GoalKey(i.x, i.y))
                        .toArray(GoalKey[]::new)))
        );

        return l;
    }

    /**
     * Proprietary class for JSON deserialization.
     */
    private static class JsonLevel {
        @SerializedName("player")
        public Position player;

        @SerializedName("monkey")
        public Position monkey;

        @SerializedName("keys")
        public List<Position> keys;

        @SerializedName("platforms")
        public List<Platform> platforms;

        @SerializedName("goal")
        public Goal goal;

        @SerializedName("move_speed")
        public float moveSpeed = 16;
        @SerializedName("jump_strength")
        public float jumpStrength = 5;
        @SerializedName("gravity_strength")
        public float gravityStrength = 8.9f;
        @SerializedName("monkey_cooldown")
        public float monkeyCooldown = 1.5f;

        protected static class Position {
            public int x;
            public int y;
        }

        protected static class Platform {
            public int x;
            public int y;
            public int width;
            public int height;
        }

        protected static class Goal {
            public int x;
            public int y;
            public int width;
            public int height;
        }
    }

}
