package monke.models;

import monke.enums.SpriteImage;
import monke.models.base.GameObject;
import monke.models.common.BoundingBox;
import monke.models.common.Collidable;

/**
 * Represents a platform in the game.
 */
public class Platform extends GameObject implements Collidable {
    private final BoundingBox boundary;

    /**
     * Constructs a Platform instance with specified coordinates and dimensions.
     * @param x X coord
     * @param y Y coord
     * @param width width
     * @param height height
     */
    public Platform(int x, int y, int width, int height) {
        super(x, y);
        this.boundary = new BoundingBox(x, y, width, height);
        this.img = SpriteImage.PLATFORM;
    }

    public BoundingBox getBounds() {
        return boundary;
    }
}
