package monke.models.entities;

import monke.enums.SpriteImage;
import monke.models.base.GameEntity;
import monke.models.common.BoundingBox;
import monke.models.common.Collidable;

/**
 * Represents a key (item) in the game.
 */
public class GoalKey extends GameEntity {

    /**
     * Constructs a GoalKey instance with specified coordinates.
     * @param x X coord
     * @param y Y coord
     */
    public GoalKey(int x, int y ) {
        super(x, y, new BoundingBox(x, y, 22, 22));
        this.img = SpriteImage.KEY;
    }

    @Override
    public void resolveCollision(Collidable c) {}
    @Override
    public void update(double dt) {}
}
