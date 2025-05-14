package monke.models;

import monke.enums.GameEvent;
import monke.enums.SpriteImage;
import monke.models.base.GameObject;
import monke.models.common.BoundingBox;
import monke.models.common.Collidable;
import monke.models.entities.Player;
import monke.utils.EventBus;

/**
 * Represents a goal object in the game.
 * A simple trigger zone that the player can collide with to win the game.
 * The goal is unlocked when the player has a certain number of keys.
 */
public class Goal extends GameObject implements Collidable {
    private final BoundingBox boundary;

    private final int locks;

    /**
     * Constructs a Goal instance with specified coordinates, dimensions, and number of locks.
     * @param x X coord
     * @param y Y coord
     * @param width width
     * @param height height
     * @param locks number of locks - therefore, number of keys needed to unlock the goal.
     */
    public Goal(int x, int y, int width, int height, int locks) {
        super(x, y);
        this.locks = locks;
        this.img = SpriteImage.GOAL;

        this.boundary = new BoundingBox(x, y, width, height);
    }

    @Override
    public BoundingBox getBounds() {
        return this.boundary;
    }

    /**
     * Checks if the player has enough keys to unlock the goal if so, publishes a WIN event.
     * @param player The player instance.
     */
    public void unlock(Player player) {
        if(player.getKeyAmount() >= locks) {
            EventBus.publish(GameEvent.WIN);
        }
    }
}
