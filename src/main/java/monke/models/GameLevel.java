package monke.models;

import monke.enums.GameEvent;
import monke.models.base.GameObject;
import monke.models.common.Collidable;
import monke.models.common.Updatable;
import monke.models.entities.*;
import monke.utils.EventBus;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

/**
 * GameLevel represents a collection of entities and static objects in the level.
 */
public class GameLevel {
    private static final Logger logger = Logger.getLogger(GameLevel.class.getName());
    //Config
    public static float jumpStrength;
    public static float moveSpeed;

    //Entities
    private Player player;
    private Monkey monkey;
    private CopyOnWriteArraySet<GoalKey> goalKeys = new CopyOnWriteArraySet<>();

    //Objects
    private CopyOnWriteArraySet<Platform> platforms;
    private Goal goal;

    /**
     * Parameterless constructor for GameLevel to enforce the use of setters.
     * @see monke.models.GameLevel#setPlayer(Player)
     * @see monke.models.GameLevel#setMonkey(Monkey)
     * @see monke.models.GameLevel#setPlatforms(CopyOnWriteArraySet)
     * @see monke.models.GameLevel#setGoalKeys(CopyOnWriteArraySet)
     * @see monke.models.GameLevel#setGoal(Goal)
     */
    public GameLevel() {
//        barrels.add(new Barrel(900, 0));
    }

    //Setters
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setMonkey(Monkey monkey) {
        this.monkey = monkey;
    }
    public void setPlatforms(CopyOnWriteArraySet<Platform> platforms) {
        this.platforms = platforms;
    }
    public void setGoalKeys(CopyOnWriteArraySet<GoalKey> goalKeys) {
        this.goalKeys = goalKeys;
    }
    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    //Getters
    public Player getPlayer() {
        return player;
    }
    public Goal getGoal() {
        return goal;
    }

    //Collection Getters
    public CopyOnWriteArraySet<GameObject> getGameObjects() {
        CopyOnWriteArraySet<GameObject> gameObjects = new CopyOnWriteArraySet<>();
        gameObjects.add(player);
        gameObjects.add(monkey);
        gameObjects.addAll(this.getBarrels());
        gameObjects.addAll(platforms);
        gameObjects.addAll(goalKeys);
        gameObjects.add(goal);
        return gameObjects;
    }
    public CopyOnWriteArraySet<Updatable> getUpdatable() {
        CopyOnWriteArraySet<Updatable> updatable = new CopyOnWriteArraySet<>();
        for (GameObject go : getGameObjects()) {
            if (go instanceof Updatable) {
                updatable.add((Updatable) go);
            }
        }
        return updatable;
    }
    public CopyOnWriteArraySet<Collidable> getCollidable() {
        CopyOnWriteArraySet<Collidable> collidable = new CopyOnWriteArraySet<>();
        for (GameObject go : getGameObjects()) {
            if (go instanceof Collidable) {
                collidable.add((Collidable) go);
            }
        }
        return collidable;
    }
    public CopyOnWriteArraySet<Barrel> getBarrels() {
        return monkey.getBarrels();
    }
    public CopyOnWriteArraySet<Platform> getPlatforms() {
        return platforms;
    }

    public CopyOnWriteArraySet<GoalKey> getItems() {
        return goalKeys;
    }

    /**
     * Removes the specified GameObject from the level.
     * @param go The GameObject to be removed.
     */
    public void destroyObject(GameObject go) {
        this.getGameObjects().remove(go);
        if (go instanceof Updatable) {
            this.getUpdatable().remove(go);
        }
        if (go instanceof Collidable) {
            this.getCollidable().remove(go);
        }
        if (go instanceof Barrel) {
            this.getBarrels().remove(go);
        }
        if (go instanceof Platform) {
            this.getPlatforms().remove(go);
        }
        if (go instanceof GoalKey) {
            this.getItems().remove(go);
        }
        if (go instanceof Player) {
            EventBus.publish(GameEvent.DIE);
        }
        logger.finest("Removed object: " + go);
    }
}