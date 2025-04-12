package monke.models.entities;

import monke.models.base.GameEntity;
import monke.models.common.BoundingBox;
import monke.models.common.Collidable;

public class Player extends GameEntity{
    public Player(int x, int y, float rotation, int width, int height) {
        super(x, y, rotation, new BoundingBox(x, y, width, height));
    }

    public void onCollision(Collidable other) {
        //TODO
        String instance = other.getClass().getName();
        switch (instance) {
            case "Barrel":
                System.out.println("Player collided with a barrel!");
                break;
            default:
                System.out.println("Player collided with: " + other);
                break;
        }
    }
}