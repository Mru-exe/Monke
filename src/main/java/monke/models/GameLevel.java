package monke.models;

import monke.enums.GameEvent;
import monke.models.base.GameObject;
import monke.models.common.Collidable;
import monke.models.common.Updatable;
import monke.models.entities.*;
import monke.utils.EventBus;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * GameLevel represents a collection of entities and static objects in the level.
 */
public class GameLevel {

    //Config
    public static float jumpStrength;
    public static float moveSpeed;

    //Entities
    private Player player;
    private Monkey monkey;
    private final CopyOnWriteArraySet<Barrel> barrels = new CopyOnWriteArraySet<>();
    private CopyOnWriteArraySet<GoalKey> goalKeys = new CopyOnWriteArraySet<>();

    //Objects
    private CopyOnWriteArraySet<Platform> platforms;
    private Goal goal;

    //Parameterless constructor to enforce setters
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
        gameObjects.addAll(barrels);
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
        return barrels;
    }
    public CopyOnWriteArraySet<Platform> getPlatforms() {
        return platforms;
    }

    public CopyOnWriteArraySet<GoalKey> getItems() {
        return goalKeys;
    }

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
    }
}