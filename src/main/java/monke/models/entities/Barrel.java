package monke.models.entities;

import monke.enums.GameEvent;
import monke.enums.SpriteImage;
import monke.models.base.GameEntity;
import monke.models.common.BoundingBox;
import monke.models.common.Collidable;
import monke.utils.EventBus;

/**
 * Represents a barrel in the game.
 * The barrel can roll and explode.
 */
public class Barrel extends GameEntity {
    private double speed;

    public Barrel(int x, int y, double speed) {
        super(x, y, new BoundingBox(x, y, 48, 48));
        this.speed = speed;
        this.img = SpriteImage.BARREL;
        this.setDamping(1.02f);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void onCollisionCustom(Collidable other) {
        if(other instanceof Player){
            EventBus.publish(GameEvent.EXIT_GAME);
        }
    }

    @Override
    public void resolveVerticalCollision(double dy, double overlapY) {
        if(this.getVelY() >= gravityStrength){
            if(Math.random() > 0.35 && this.getVelX() != 0) this.setSpeed(-speed);
            this.setVelX(speed);
        }
        //check if this is the initial collision (first call)
        if(dy > 0){
            this.setVelY(gravityStrength);
        } else {
            this.setCoords(this.getX(), this.getY() - overlapY);
            this.setVelY(0);
        }
    }
}
