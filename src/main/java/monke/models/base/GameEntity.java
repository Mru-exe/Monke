package monke.models.base;

import monke.models.common.BoundingBox;
import monke.models.common.Collidable;
import monke.models.common.Updatable;

import java.util.logging.Logger;

/**
 * Abstract class representing a game entity that can be rendered and has collision detection capabilities.
 */
public abstract class GameEntity extends GameObject implements Collidable, Updatable {
    private static final Logger logger = Logger.getLogger(GameEntity.class.getName());

    private BoundingBox boundary = null;

    private double velX = 0;
    private double velY = 0;

    private double maxVelX = 10;
    private double maxVelY = 10;

    public static final double gravityStrength = 0.2d;
    private double frictionStrength = 1d;

    public GameEntity(double x, double y) {
        super(x, y);
    }
    public GameEntity(double x, double y, BoundingBox boundary) {
        super(x, y);
        this.boundary = boundary;
    }

    public BoundingBox getBounds(){
        return this.boundary;
    };

    @Override
    public void update(double dt) {
        //First move, then recalculate physics; otherwise collisions are buggy
        this.setCoords(this.getX() + this.velX, this.getY() + this.velY);
        this.updateBounds(this.getX(), this.getY());

        this.velY += gravityStrength;
        this.velX *= frictionStrength;
        if(Math.abs(velX) <= 0.125d){ //friction threshold
            frictionStrength = 1;
            velX = 0;
        }
    }

    public double getVelY() {
        return velY;
    }
    public double getVelX() {
        return velX;
    }

    public void applyForceX(double velX) {
        this.frictionStrength = 1d;
        this.velX = velX;
    }
    public void applyForceY(double velY) {
        this.velY = velY;
    }

    public void applyFriction(double frictionStrength) {
        this.frictionStrength = frictionStrength;
    }
}
