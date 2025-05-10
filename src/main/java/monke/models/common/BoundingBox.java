package monke.models.common;

/**
 * Represents a bounding box for collision detection.
 */
public class BoundingBox {
    private double x;
    private double y;
    private double width;
    private double height;

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

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}

