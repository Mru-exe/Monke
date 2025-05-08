package monke.models.common;

/**
 * Interface representing a collidable object in the game.
 * Any object that can collide with others should implement this interface.
 */
public interface Collidable {

    /**
     * Retrieves the bounding box of the object for collision detection.
     *
     * @return a BoundingBox representing the collision boundary.
     */
    BoundingBox getBounds();

    default void updateBounds(double x, double y) {
        BoundingBox bounds = this.getBounds();
        if(bounds != null) {
            bounds.setX(x);
            bounds.setY(y);
        }
    }
    default void updateBounds(int x, int y, int width, int height) {
        BoundingBox bounds = this.getBounds();
        bounds.setX(x);
        bounds.setY(y);
        bounds.setWidth(width);
        bounds.setHeight(height);
    }

    /**
     * Defines behavior when a collision occurs with another Collidable object.
     *
     * @param other the other Collidable object involved in the collision.
     */
    void onCollision(Collidable other);
}
