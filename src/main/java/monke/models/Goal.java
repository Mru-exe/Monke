package monke.models;

import monke.models.common.BoundingBox;
import monke.models.common.Collidable;

public class Goal implements Collidable {
    private int x;
    private int y;
    private int width;
    private int height;

    private BoundingBox boundary;

    public Goal(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.boundary = new BoundingBox(x, y, width, height);
    }

    public BoundingBox getBounds() {
        return this.boundary;
    }

    @Override
    public void onCollision(Collidable other) {
        //win game
    }

    public void setBounds(BoundingBox boundary) {
        this.boundary = boundary;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
