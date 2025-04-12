package monke.models.entities;

import monke.models.base.GameEntity;

public class Barrel extends GameEntity {
    private int speed = 5;

    public Barrel(int x, int y, float rotation) {
        super(x, y, rotation);
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
