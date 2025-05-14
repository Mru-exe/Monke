package monke.enums;

/**
 * Enum representing image paths and dimensions for various sprites used in the game.
 */
public enum SpriteImage {
    NOTEXTURE("/sprites/notexture.png", 16, 16),
    MONKEY("/sprites/monkey.png", 64, 64),
    BARREL("/sprites/barrel.png", 48, 48),
    PLATFORM("/sprites/platform.png", 24, 24),
    KEY("/sprites/key.png", 22, 22),
    GOAL("/sprites/door.png", 64, 64),
    PLAYER("/sprites/player.png", 32, 32);

    private final String path;
    private final int height;
    private final int width;

    SpriteImage(String path, int height, int width) {
        this.path = path;
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getPath() {
        return path;
    }
}
