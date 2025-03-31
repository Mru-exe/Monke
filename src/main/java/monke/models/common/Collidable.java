package monke.models.common;

public interface Collidable extends Positionable {

    /**
     * Retrieves the bounding box of the object for collision detection.
     *
     * @return a BoundingBox representing the collision boundary.
     */
    BoundingBox getBounds();

    /**
     * Defines behavior when a collision occurs with another Collidable object.
     *
     * @param other the other Collidable object involved in the collision.
     */
    void onCollision(Collidable other);
}
