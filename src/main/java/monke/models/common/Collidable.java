package monke.models.common;

/**
 * Interface representing a collidable object in the game.
 * Any object that can collide with others should implement this interface.
 */
public interface Collidable {

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

    static boolean areOverlapping(Collidable c1, Collidable c2) {
        BoundingBox b1 = c1.getBounds();
        BoundingBox b2 = c2.getBounds();

        double dx = Math.abs((b1.getX() + b1.getWidth() * 0.5) - (b2.getX() + b2.getWidth() * 0.5));
        double dy = Math.abs((b1.getY() + b1.getHeight() * 0.5) - (b2.getY() + b2.getHeight() * 0.5));
        double halfWidth = (b1.getWidth() + b2.getWidth()) * 0.5;
        double halfHeight = (b1.getHeight() + b2.getHeight()) * 0.5;
        return dx < halfWidth && dy < halfHeight;
    }

    default boolean overlaps(Collidable other) {
        return areOverlapping(this, other);
    }

    default void resolveCollision(Collidable c) {
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

    private double computeDelta(double pos1, double size1, double pos2, double size2) {
        return (pos1 + size1 * 0.5) - (pos2 + size2 * 0.5);
    }

    private double computeOverlap(double size1, double size2, double delta) {
        return (size1 + size2) * 0.5 - Math.abs(delta);
    }

    default void resolveHorizontalCollision(double dx, double overlapX){}
    default void resolveVerticalCollision(double dy, double overlapY){}
    default void onCollisionCustom(Collidable other) {
        // Custom collision logic can be implemented in subclasses
    }
}
