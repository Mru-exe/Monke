package monke.models;

import monke.models.base.GameObject;
import monke.models.common.Collidable;
import monke.models.common.Updatable;
import monke.models.entities.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * GameLevel represents a collection of entities and static objects in the level.
 */
public class GameLevel {

    //Entities
    private Player player;
    private Monkey monkey;
    private Set<Barrel> barrels = new HashSet<>();

    //Static
    private Set<Platform> platforms;
    private Goal goal;

    //Parameterless constructor to enforce setters
    public GameLevel() {}

    //Setters
    public void setPlayer(Player player) {

        this.player = player;
    }
    public void setMonkey(Monkey monkey) {
        this.monkey = monkey;
    }
    public void setPlatforms(Set<Platform> platforms) {
        this.platforms = platforms;
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
    public Collection<GameObject> getGameObjects() {
        Set<GameObject> gameObjects = new HashSet<>();
        gameObjects.add(player);
        gameObjects.add(monkey);
//        gameObjects.addAll(barrels);
        gameObjects.addAll(platforms);
//        gameObjects.add(goal);
        return gameObjects;
    }
    public Collection<Updatable> getUpdatable() {
        Set<Updatable> updatable = new HashSet<>();
        updatable.add(player);
//        updatable.add(monkey);
        return updatable;
    }
    public Collection<Collidable> getCollidable() {
        Set<Collidable> collidable = new HashSet<>();
        collidable.add(player);
//        collidable.addAll(barrels);
        collidable.addAll(platforms);
//        collidable.add(goal);
        return collidable;
    }
    public Collection<Barrel> getBarrels() {
        return barrels;
    }
}