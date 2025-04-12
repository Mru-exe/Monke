package monke.models.base;
import java.util.UUID;

public abstract class GameObject {
    protected final UUID id;

    private int x;
    private int y;
    protected float rotation = 0.0f; // Rotation in degrees

    public GameObject(int x, int y, float rotation) {
        this.id = UUID.randomUUID();

        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public UUID getId() {
        return id;
    }

    public String getDebugString() {
        return this.getClass().getSimpleName() + "[" + this.id + "]";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getRotation() {
        return rotation;
    }

    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setCoords(int x, int y, float rotation) {
        this.rotation = rotation;
        this.x = x;
        this.y = y;
    }
}
