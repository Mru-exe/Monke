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

    private float velX = 0;
    private float velY = 0;
    public static final float gravityStrength = 0.5f;

    public GameEntity(float x, float y) {
        super(x, y);
    }
    public GameEntity(float x, float y, BoundingBox boundary) {
        super(x, y);
        this.boundary = boundary;
    }

    public void setBounds(BoundingBox boundary) {
        this.boundary = boundary;
    }

    public BoundingBox getBounds(){
        return this.boundary;
    };

    @Override
    public void onCollision(Collidable other) {
        //TODO
    }

    @Override
    public void update() {
        this.velY += gravityStrength;
        this.setCoords(this.getX() + this.velX, this.getY() + (this.velY));
    }

    public void setBoundary(BoundingBox boundary) {
        this.boundary = boundary;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }
}
