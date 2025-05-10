package monke.enums;

public enum SpriteImage {
    NOTEXTURE("/sprites/notexture.png"),
    PLAYER("/sprites/player/player-still.png"),
    BARREL("/sprites/barrel2.png");
//    ITEM("item/item.png"),
//    BACKGROUND("background/background.png"),
//    ENEMY("enemy/enemy.png");

    private final String path;

    SpriteImage(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
