package monke.models.entities;

import monke.models.base.GameEntity;
import monke.models.common.BoundingBox;

/**
 * Represents an item (powerup) in the game.
 */
public class GameItem extends GameEntity {
    public enum ItemType {
        HAMMER,
        KEY
    }

    private final ItemType type;

    public GameItem(int x, int y, ItemType type) {
        super(x, y, new BoundingBox(x, y, 16, 16));
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }
}
