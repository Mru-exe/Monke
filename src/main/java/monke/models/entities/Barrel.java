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
    private int speed = -5;

    public Barrel(int x, int y) {
        super(x, y, new BoundingBox(x, y, 32, 32));
        this.img = SpriteImage.BARREL;
        this.applyForceX(0);
        this.setDamping(1.01f);
        speed = (x > 430) ? -speed : speed;
    }

    public void roll() {
        // Logic to roll the barrel
    }

    public void explode() {
        // Logic to explode the barrel
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void onCollisionCustom(Collidable other) {
        if(other instanceof Player){
            EventBus.publish(GameEvent.EXIT_GAME);
        }
    }

    @Override
    public void resolveHorizontalCollision(double dx, double overlapX) {
        double shiftX = dx > 0 ? overlapX : -overlapX;
        this.setCoords(this.getX() + shiftX, this.getY());
//        this.applyForceX(0);
    }

    @Override
    public void resolveVerticalCollision(double dy, double overlapY) {
        if(this.getVelY() >= gravityStrength){
            this.setSpeed(-speed);
            this.applyForceX(speed);
        }
        //check if this is the initial collision (first call)
        if(dy > 0){
            this.applyForceY(gravityStrength);
        } else {
            this.setCoords(this.getX(), this.getY() - overlapY);
            this.applyForceY(0);
        }
    }
}
