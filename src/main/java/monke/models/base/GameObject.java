package monke.models.base;
import javafx.scene.Group;

/**
 * Base class for all game objects.
 * This class provides a unique ID and basic position and rotation properties.
 */
public abstract class GameObject {
    protected final long id;

    private Group fxSprite;

    private float x;
    private float y;

    public GameObject(float x, float y) {

        this.id = java.lang.System.nanoTime();

        this.x = x;
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public String getDebugString() {
        return this.getClass().getSimpleName() + "[" + this.id + "]";
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setCoords(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Group getFxSprite() {
        return fxSprite;
    }

    public void setFxSprite(Group fxSprite) {
        this.fxSprite = fxSprite;
    }

}
