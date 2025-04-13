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

    /**
     * Sets the bounding box of the object for collision detection.
     *
     * @param boundary a BoundingBox representing the new collision boundary.
     */
    void setBounds(BoundingBox boundary);

    /**
     * Defines behavior when a collision occurs with another Collidable object.
     *
     * @param other the other Collidable object involved in the collision.
     */
    void onCollision(Collidable other);
}
