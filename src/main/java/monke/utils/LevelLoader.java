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

/**
 * LevelLoader is responsible for loading levels from JSON files.
 * It deserializes the level data and returns a Level object.
 */
public class LevelLoader {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Loads a level from a external file.
     * @param levelFilePath
     * @return Level object of the level
     */
    public static GameLevel loadLevel(String levelFilePath) {
        String json;
        try(InputStream stream = MonkeyGame.class.getClassLoader().getResourceAsStream(levelFilePath)) {
            json = new String(stream.readAllBytes());
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(json != null) {
            JsonLevel level = gson.fromJson(json, JsonLevel.class);
            GameLevel gameLevel = convertToPlayable(level);
            return gameLevel;
        }
        return null;
    }

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
                new Monkey(level.monkey.x, level.monkey.y, l.getBarrels())
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

    public static String serializeLevel(GameLevel level) {
        String out = gson.toJson(level);
        return out;
    }

    protected static class JsonLevel {
        @SerializedName("level_name")
        public String levelName;

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

        private static class Position {
            public int x;
            public int y;
        }

        private static class Platform {
            public int x;
            public int y;
            public int width;
            public int height;
        }

        private static class Goal {
            public int x;
            public int y;
            public int width;
            public int height;
        }
    }

}
