package monke.models.base;

import monke.models.common.BoundingBox;
import monke.models.common.Collidable;

/**
 * Abstract class representing a game entity that can be rendered and has collision detection capabilities.
 */
public abstract class GameEntity extends GameObject implements Collidable {
    private BoundingBox boundary = null;

    public GameEntity(int x, int y, float rotation) {
        super(x, y, rotation);
    }
    public GameEntity(int x, int y, float rotation, BoundingBox boundary) {
        super(x, y, rotation);
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
}
