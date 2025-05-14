package monke.models.entities;

import monke.enums.SpriteImage;
import monke.models.base.GameEntity;
import monke.models.common.BoundingBox;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a player in the game.
 */
public class Player extends GameEntity{
    private static final Logger logger = Logger.getLogger(Player.class.getName());
    private final List<GoalKey> inventory = new ArrayList<>();

    public Player(int x, int y) {
        super(x, y, new BoundingBox(x, y, 32, 32));
        this.img = SpriteImage.PLAYER;
    }

    public int getKeyAmount() {
        return this.inventory.size();
    }

    public void pickupKey(GoalKey goalKey) {
        this.inventory.add(goalKey);
        logger.info("Player picked up item: " + goalKey.toString());
    }

    @Override
    public void resolveHorizontalCollision(double dx, double overlapX) {
        double shiftX = dx > 0 ? overlapX : -overlapX;
        this.setCoords(this.getX() + shiftX, this.getY());
        this.setVelX(0);
    }

    @Override
    public void resolveVerticalCollision(double dy, double overlapY) {
        if(dy > 0){
            this.setVelY(gravityStrength);
        } else {
            this.setCoords(this.getX(), this.getY() - overlapY);
            this.setVelY(0);
        }
    }
}