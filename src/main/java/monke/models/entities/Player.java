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
        //TODO collision
    }
}