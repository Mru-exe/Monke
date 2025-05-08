package monke.models.common;

/**
 * Represents a bounding box for collision detection.
 */
public class BoundingBox {
    private double x;
    private double y;
    private double width;
    private double height;
    private static final double EPS = 0;


    public BoundingBox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(double width) {
        this.width = width;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Checks if this bounding box intersects with another.
     *
     * @param other the other BoundingBox
     * @return true if the boxes overlap, false otherwise
     */
    public boolean intersects(BoundingBox other) {
        if (other == null){
            return false;
        } 
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
    }
}

