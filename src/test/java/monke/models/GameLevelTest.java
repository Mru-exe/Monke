package monke.models;

import monke.enums.GameEvent;
import monke.models.base.GameObject;
import monke.models.entities.Barrel;
import monke.models.Goal;
import monke.models.entities.GoalKey;
import monke.models.entities.Monkey;
import monke.models.entities.Player;
import monke.utils.EventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArraySet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GameLevel.
 */
class GameLevelTest {

    private GameLevel level;
    private Player player;
    private Monkey monkey;
    private Barrel barrel;
    private GoalKey key;
    private Goal goal;

    private boolean eventFired;

    @BeforeEach
    void setUp() {
        level = new GameLevel();

        player = new Player(0, 0);
        barrel = new Barrel(2, 3, 2f);
        monkey = new Monkey(10, 5, 1f);
        key = new GoalKey(4, 4);
        goal = new Goal(10, 10, 10, 10 ,5);

        // Set up level
        level.setPlayer(player);
        level.setMonkey(monkey);

        // platforms can be empty for now
        level.setPlatforms(new CopyOnWriteArraySet<>());

        // keys
        CopyOnWriteArraySet<GoalKey> keys = new CopyOnWriteArraySet<>();
        keys.add(key);
        level.setGoalKeys(keys);

        // goal
        level.setGoal(goal);

        // Subscribe to DIE event
        eventFired = false;
        EventBus.subscribe(GameEvent.DIE, () -> eventFired = true);
    }

    @Test
    void testGetGameObjectsContainsAll() {
        // add a barrel to its own collection
        level.getBarrels().add(barrel);

        CopyOnWriteArraySet<GameObject> all = level.getGameObjects();
        assertEquals(5, all.size(), "player, monkey, barrel, key, goal");

        assertTrue(all.contains(player));
        assertTrue(all.contains(monkey));
        assertTrue(all.contains(barrel));
        assertTrue(all.contains(key));
        assertTrue(all.contains(goal));
    }

    @Test
    void testDestroyBarrelRemovesFromCollections() {
        level.getBarrels().add(barrel);
        assertTrue(level.getGameObjects().contains(barrel));

        level.destroyObject(barrel);

        assertFalse(level.getBarrels().contains(barrel));
        assertFalse(level.getGameObjects().contains(barrel));
        assertFalse(level.getUpdatable().contains(barrel));
        assertFalse(level.getCollidable().contains(barrel));
    }

    @Test
    void testDestroyPlayerFiresDieEvent() {
        assertTrue(level.getGameObjects().contains(player));

        level.destroyObject(player);
        assertTrue(eventFired, "Destroying the player should publish DIE");
    }
}
