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
 */
public class Goal extends GameObject implements Collidable {
    private BoundingBox boundary;

    private final int locks;

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

    public void unlock(Player player) {
        if(player.getKeyAmount() >= locks) {
            EventBus.publish(GameEvent.WIN);
        }
    }
}
