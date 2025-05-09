package monke.models.entities;

import monke.enums.PlayerState;
import monke.models.base.GameEntity;
import monke.models.common.BoundingBox;
import monke.models.common.Collidable;

/**
 * Represents a player in the game.
 */
public class Player extends GameEntity{

    public Player(int x, int y, int boundaryWidth, int boundaryHeight) {
        super(x, y, new BoundingBox(x, y, boundaryWidth, boundaryHeight));
    }

    @Override
    public void onCollision(Collidable other) {
        this.resolveCollision(this, other);
    }

    private void resolveCollision(GameEntity e, Collidable c) {
        BoundingBox b1 = e.getBounds();
        BoundingBox b2 = c.getBounds();

        // Compute centers
        double eCenterX = b1.getX() + b1.getWidth()  * 0.5;
        double eCenterY = b1.getY() + b1.getHeight() * 0.5;
        double cCenterX = b2.getX() + b2.getWidth()  * 0.5;
        double cCenterY = b2.getY() + b2.getHeight() * 0.5;

        // Compute current delta between centers
        double dx = eCenterX - cCenterX;
        double dy = eCenterY - cCenterY;

        // Compute combined half‐sizes
        double halfWidth  = (b1.getWidth()  + b2.getWidth())  * 0.5;
        double halfHeight = (b1.getHeight() + b2.getHeight()) * 0.5;

        // Early‐out if no overlap
        if (Math.abs(dx) >= halfWidth || Math.abs(dy) >= halfHeight) {
            return;
        }

        // Compute overlap on each axis
        double overlapX = halfWidth  - Math.abs(dx);
        double overlapY = halfHeight - Math.abs(dy);

        // Resolve along the axis of least penetration
        if (overlapX < overlapY) {
            // Horizontal collision
            double shiftX = dx > 0 ? overlapX : -overlapX;
            e.setCoords(e.getX() + shiftX, e.getY());
            e.applyForceX(0);              // stop horizontal motion
        } else {
            // Vertical collision
            double shiftY = dy > 0 ? overlapY : -overlapY;
            e.setCoords(e.getX(), e.getY() + shiftY);
            e.applyForceY(0);              // stop vertical motion

            // If entity was above the collider (dy < 0) and we pushed it up (shiftY < 0),
            // it's landed on top of the platform:
            if (dy < 0) {
                // e.setOnGround(true);     // mark grounded, reset jumps, etc.
            }
        }

        // Update the bounding‐box to the new position
        e.updateBounds(e.getX(), e.getY());
    }


}