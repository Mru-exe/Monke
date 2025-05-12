package monke.models.entities;

import monke.models.base.GameEntity;
import monke.models.common.BoundingBox;

/**
 * Represents an item (powerup) in the game.
 */
public class Item extends GameEntity {
    public enum ItemType {
        HAMMER,
        KEY
    };

    private ItemType type;

    public Item(int x, int y, ItemType type) {
        super(x, y, new BoundingBox(x, y, 16, 16));
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }
}
