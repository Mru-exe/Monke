package monke.models.base;

import monke.models.common.BoundingBox;
import monke.models.common.Collidable;
import monke.models.common.Updatable;

import java.util.logging.Logger;

/**
 * Abstract class representing a game entity that can be updated and has collision detection capabilities.
 */
public abstract class GameEntity extends GameObject implements Collidable, Updatable {
    private static final Logger logger = Logger.getLogger(GameEntity.class.getName());

    private final BoundingBox boundary;

    private double velX = 0;
    private double velY = 0;

    public static double gravityStrength = 8.9d;
    private double damping = 0.7d;

    public GameEntity(double x, double y, BoundingBox boundary) {
        super(x, y);
        this.boundary = boundary;
    }

    public BoundingBox getBounds(){
        return this.boundary;
    }

    @Override
    public void update(double dt) {
        velY += gravityStrength * dt;
        x    += velX * dt;
        y    += velY * dt;
        this.updateBounds(x, y);
        velX *= Math.pow(damping, dt);
        if(Math.abs(velX) <= damping){
            velX = 0;
        }
        logger.finest("Updated " + this);
    }

    public double getVelY() {
        return velY;
    }
    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }
    public void setVelY(double velY) {
        this.velY = velY;
    }

    public void setDamping(double damping) {
        this.damping = damping;
    }
    public double getDamping() {
        return this.damping;
    }
}
