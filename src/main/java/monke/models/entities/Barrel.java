package monke.models.entities;

import monke.models.base.GameEntity;

/**
 * Represents a barrel in the game.
 * The barrel can roll and explode.
 */
public class Barrel extends GameEntity {
    private int speed = 5;

    public Barrel(int x, int y) {
        super(x, y);
        this.speed = 10; // Default speed
    }

    public void roll() {
        // Logic to roll the barrel
    }

    public void explode() {
        // Logic to explode the barrel
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }    
}
