package monke.models;

import monke.models.base.GameObject;
import monke.models.common.BoundingBox;
import monke.models.common.Collidable;

//TODO

/**
 * Represents a platform in the game.
 */
public class Platform extends GameObject implements Collidable {
    private int width;
    private int height;

    private final BoundingBox boundary;

    public Platform(int x, int y, float rotation, int width, int height) {
        super(x, y, rotation);
        this.boundary = new BoundingBox(x, y, width, height);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BoundingBox getBounds() {
        return boundary;
    }

    @Override
    public void setBounds(BoundingBox boundary) {
        return;
    }

    @Override
    public void onCollision(Collidable other) {

    }

    @Override
    public String toString() {
        return "Platform{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

}
