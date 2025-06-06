package monke.models.base;
import javafx.scene.Group;
import monke.enums.SpriteImage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Base class for all game objects.
 * This class provides a unique ID and basic position and rotation properties.
 */
public abstract class GameObject {
    private static final Map<Class<? extends GameObject>, AtomicInteger> COUNTERS = new ConcurrentHashMap<>();

    private final int id;

    private Group fxSprite;
    protected SpriteImage img;

    protected double x;
    protected double y;

    public GameObject(double x, double y) {
        AtomicInteger counter = COUNTERS.computeIfAbsent(
                this.getClass(),
                cls -> new AtomicInteger(0)
        );
        this.id = counter.incrementAndGet();

        this.img = SpriteImage.NOTEXTURE;

        this.x = x;
        this.y = y;
    }

    /**
     * @return String in format `ClassName`#`SerialNumber`
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "#" + this.id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setCoords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Group getFxSprite() {
        return fxSprite;
    }

    public void setFxSprite(Group fxSprite) {
        this.fxSprite = fxSprite;
    }

    public SpriteImage getImg() {
        return img;
    }
}
