package monke.models.entities;

import monke.enums.PlayerState;
import monke.models.base.GameEntity;
import monke.models.common.BoundingBox;

/**
 * Represents a player in the game.
 */
public class Player extends GameEntity{

    private PlayerState state;
    private final float jumpStrength = 8;
    private final float moveSpeed = 5;

    public Player(int x, int y, int boundaryWidth, int boundaryHeight) {
        super(x, y, new BoundingBox(x, y, boundaryWidth, boundaryHeight));
    }

    public float getJumpStrength() {
        return jumpStrength;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }
}