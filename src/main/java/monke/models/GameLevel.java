package monke.models;

import monke.models.base.GameObject;
import monke.models.common.Collidable;
import monke.models.common.Updatable;
import monke.models.entities.*;
import monke.utils.EventBus;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * GameLevel represents a collection of entities and static objects in the level.
 */
public class GameLevel {

    //Entities
    private Player player;
    private Monkey monkey;
    private CopyOnWriteArraySet<Barrel> barrels = new CopyOnWriteArraySet<>();
    private CopyOnWriteArraySet<GameItem> gameItems = new CopyOnWriteArraySet<>();

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
    public void setItems(CopyOnWriteArraySet<GameItem> gameItems) {
        this.gameItems = gameItems;
    }
    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    //Getters
    public Player getPlayer() {
        return player;
    }
    public Monkey getMonkey() {
        return monkey;
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
        gameObjects.addAll(gameItems);
//        gameObjects.add(goal);
        return gameObjects;
    }
    public CopyOnWriteArraySet<Updatable> getUpdatable() {
        CopyOnWriteArraySet<Updatable> updatable = new CopyOnWriteArraySet<>();
        updatable.add(player);
        updatable.addAll(barrels);
        updatable.add(monkey);
        return updatable;
    }
    public CopyOnWriteArraySet<Collidable> getCollidable() {
        CopyOnWriteArraySet<Collidable> collidable = new CopyOnWriteArraySet<>();
        collidable.add(player);
        collidable.addAll(barrels);
        collidable.addAll(platforms);
        collidable.add(monkey);
        collidable.addAll(gameItems);
//        collidable.add(goal);
        return collidable;
    }
    public CopyOnWriteArraySet<Barrel> getBarrels() {
        return barrels;
    }
    public CopyOnWriteArraySet<Platform> getPlatforms() {
        return platforms;
    }

    public CopyOnWriteArraySet<GameItem> getItems() {
        return gameItems;
    }

    public void destroyObject(GameObject go) {
        if (go instanceof Barrel) {
            barrels.remove(go);
        } else if (go instanceof Platform) {
            platforms.remove(go);
        } else if (go instanceof Player) {
            this.player = null;
            System.out.println("GG player died XD");
            EventBus.publish(monke.enums.GameEvent.EXIT_GAME);
        }
        else if (go instanceof Monkey) {
            System.out.println("rip");
            this.monkey = null;
        }
    }
}