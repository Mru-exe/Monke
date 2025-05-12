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
    private Item activeItem;

    public Player(int x, int y) {
        super(x, y, new BoundingBox(x, y, 32, 32));
        this.img = SpriteImage.PLAYER;
    }

    public void pickupItem(Item item) {
        if(this.activeItem == null){
            this.activeItem = item;
            logger.info("Player picked up item: " + item.getType());
        }
    }

    public void dropItem() {
        this.activeItem = null;
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