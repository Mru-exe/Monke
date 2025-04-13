package monke.models.common;

/**
 * Represents a bounding box for collision detection.
 */
public class BoundingBox {
    private float x, y, width, height;

    public BoundingBox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    /**
     * Checks if this bounding box intersects with another.
     *
     * @param other the other BoundingBox
     * @return true if the boxes overlap, false otherwise
     */
    public boolean intersects(BoundingBox other) {
        if (other == null){
            //log
            return false; 
        } 
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
    }
}

