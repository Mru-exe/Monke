package monke.models.common;

/**
 * Interface representing a collidable object in the game.
 * Any object that can collide with others should implement this interface.
 */
public interface Collidable {

    BoundingBox getBounds();

    /**
     * Updates the position of the collidable object.
     * @param x X coord
     * @param y Y coord
     */
    default void updateBounds(double x, double y) {
        BoundingBox bounds = this.getBounds();
        if(bounds != null) {
            bounds.setX(x);
            bounds.setY(y);
        }
    }

    /**
     * Checks if two collidable objects are overlapping.
     * @param c1 First collidable object
     * @param c2 Second collidable object
     * @return true if they are overlapping, false otherwise
     */
    static boolean areOverlapping(Collidable c1, Collidable c2) {
        if(c1 == null || c2 == null) {
            return false;
        }
        BoundingBox b1 = c1.getBounds();
        BoundingBox b2 = c2.getBounds();

        double dx = Math.abs((b1.getX() + b1.getWidth() * 0.5) - (b2.getX() + b2.getWidth() * 0.5));
        double dy = Math.abs((b1.getY() + b1.getHeight() * 0.5) - (b2.getY() + b2.getHeight() * 0.5));
        double halfWidth = (b1.getWidth() + b2.getWidth()) * 0.5;
        double halfHeight = (b1.getHeight() + b2.getHeight()) * 0.5;
        return dx < halfWidth && dy < halfHeight;
    }

    /**
     * Checks if this collidable object overlaps with another collidable object.
     * @param other The other collidable object
     * @return true if they are overlapping, false otherwise
     * @see #areOverlapping(Collidable, Collidable)
     */
    default boolean overlaps(Collidable other) {
        return areOverlapping(this, other);
    }

    /**
     * Calculates the collision resolution for this collidable object with another collidable object.
     * This method determines the direction of the collision and delegates resolution accordingly.
     * @param c The other collidable object
     * @see #resolveHorizontalCollision(double, double)
     * @see #resolveVerticalCollision(double, double)
     */
    default void resolveCollision(Collidable c) {
        if(c == null) {
            return;
        }
        BoundingBox self = this.getBounds();
        BoundingBox other = c.getBounds();

        double dx = computeDelta(self.getX(), self.getWidth(), other.getX(), other.getWidth());
        double dy = computeDelta(self.getY(), self.getHeight(), other.getY(), other.getHeight());

        double overlapX = computeOverlap(self.getWidth(), other.getWidth(), dx);
        double overlapY = computeOverlap(self.getHeight(), other.getHeight(), dy);

        this.onCollisionCustom(c);
        if (overlapX < overlapY) {
            resolveHorizontalCollision(dx, overlapX);
        } else {
            resolveVerticalCollision(dy, overlapY);
        }
    }

    /**
     * Computes the delta between two positions and sizes.
     * @param pos1
     * @param size1
     * @param pos2
     * @param size2
     * @return The delta value
     */
    private double computeDelta(double pos1, double size1, double pos2, double size2) {
        return (pos1 + size1 * 0.5) - (pos2 + size2 * 0.5);
    }

    /**
     * Computes the overlap between two sizes and a delta value.
     * @param size1
     * @param size2
     * @param delta
     * @return The overlap value
     */
    private double computeOverlap(double size1, double size2, double delta) {
        return (size1 + size2) * 0.5 - Math.abs(delta);
    }

    /**
     * Resolves the horizontal collision by adjusting the position of the collidable object.
     * @param dx The delta value in the x direction
     * @param overlapX The overlap value in the x direction
     */
    default void resolveHorizontalCollision(double dx, double overlapX){}

    /**
     * Resolves the vertical collision by adjusting the position of the collidable object.
     * @param dy The delta value in the y direction
     * @param overlapY The overlap value in the y direction
     */
    default void resolveVerticalCollision(double dy, double overlapY){}

    /**
     * Custom collision handling method. Called always before the rest of collision resolution.
     * @param other The other collidable object
     * @see #resolveCollision(Collidable)
     */
    default void onCollisionCustom(Collidable other) {
        // Custom collision logic can be implemented in subclasses
    }
}
