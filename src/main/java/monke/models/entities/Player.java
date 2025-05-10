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
    public void resolveHorizontalCollision(double dx, double overlapX) {
        double shiftX = dx > 0 ? overlapX : -overlapX;
        this.setCoords(this.getX() + shiftX, this.getY());
        this.applyForceX(0);
    }

    @Override
    public void resolveVerticalCollision(double dy, double overlapY) {
        if(dy > 0){
            this.applyForceY(gravityStrength);
        } else {
            this.setCoords(this.getX(), this.getY() - overlapY);
            this.applyForceY(0);
        }
    }
}