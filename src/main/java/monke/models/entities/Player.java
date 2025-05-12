package monke.models.entities;

import monke.enums.SpriteImage;
import monke.models.base.GameEntity;
import monke.models.common.BoundingBox;
import monke.models.common.Collidable;

import java.util.logging.Logger;

/**
 * Represents a player in the game.
 */
public class Player extends GameEntity{
    private static final Logger logger = Logger.getLogger(Player.class.getName());
    private GameItem activeGameItem;

    public Player(int x, int y) {
        super(x, y, new BoundingBox(x, y, 32, 32));
        this.img = SpriteImage.PLAYER;
    }

    public GameItem getActiveItem() {
        return activeGameItem;
    }

    private void pickupItem(GameItem gameItem) {
        if(this.activeGameItem == null){
            this.activeGameItem = gameItem;
            logger.info("Player picked up item: " + gameItem.getType());
        } else {
            logger.fine("Cant pickup more than 1 items");
        }
    }

    public void dropItem() {
        this.activeGameItem = null;
        logger.info("Player dropped item");
    }

    @Override
    public void onCollisionCustom(Collidable other) {
        if(other instanceof Barrel){
            //lose game
        }
    }

    @Override
    public void resolveHorizontalCollision(double dx, double overlapX) {
        double shiftX = dx > 0 ? overlapX : -overlapX;
        this.setCoords(this.getX() + shiftX, this.getY());
        this.applyForceX(0);
    }

    @Override
    public void resolveVerticalCollision(double dy, double overlapY) {
        if(dy > 0){
            this.applyForceY(gravityStrength);
        } else {
            this.setCoords(this.getX(), this.getY() - overlapY);
            this.applyForceY(0);
        }
    }
}