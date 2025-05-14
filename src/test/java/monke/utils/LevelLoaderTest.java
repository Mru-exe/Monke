package monke.utils;

import monke.models.GameLevel;
import monke.models.Goal;
import monke.models.Platform;
import monke.models.base.GameEntity;
import monke.models.entities.GoalKey;
import monke.models.entities.Player;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArraySet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link LevelLoader}.
 *
 * Requires a test resource file `test_level.json` placed under src/test/resources:
 */
class LevelLoaderTest {

    @Test
    void testLoadLevel_successful() {
        GameLevel level = LevelLoader.loadLevel("test_level.json");
        assertNotNull(level, "Level should be loaded successfully");

        // Static config applied
        assertEquals(20.0f, GameLevel.moveSpeed, 1e-6);
        assertEquals(7.0f, GameLevel.jumpStrength, 1e-6);
        assertEquals(9.8f, GameEntity.gravityStrength, 1e-6);

        // Player
        Player player = level.getPlayer();
        assertNotNull(player);
        assertEquals(1, player.getBounds().getX());
        assertEquals(2, player.getBounds().getY());

        // Platforms
        CopyOnWriteArraySet<Platform> platforms = level.getPlatforms();
        assertEquals(1, platforms.size());
        Platform p = platforms.iterator().next();
        assertEquals(9, p.getBounds().getX());
        assertEquals(10, p.getBounds().getY());
        assertEquals(11, p.getBounds().getWidth());
        assertEquals(12, p.getBounds().getHeight());

        // Keys
        CopyOnWriteArraySet<GoalKey> keys = level.getItems();
        assertEquals(2, keys.size());

        // Goal
        Goal goal = level.getGoal();
        assertNotNull(goal);
        assertEquals(13, goal.getBounds().getX());
        assertEquals(14, goal.getBounds().getY());
        assertEquals(15, goal.getBounds().getWidth());
        assertEquals(16, goal.getBounds().getHeight());
    }

    @Test
    void testLoadLevel_missingResource_returnsNull() {
        GameLevel level = LevelLoader.loadLevel("nonexistent.json");
        assertNull(level, "Loading a non-existent file should return null");
    }
}
