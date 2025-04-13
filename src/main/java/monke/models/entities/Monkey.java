package monke.models.entities;

import monke.models.base.GameEntity;

/**
 * Represents a monkey in the game.
 * Basically just origin coords for barrel spawns.
 */
public class Monkey extends GameEntity{
    public Monkey(int x, int y, float rotation) {
        super(x, y, rotation);
    }

    public void throwBarrel(int barrelSpeed) {
        Barrel barell = new Barrel(this.getX(), this.getY(), 0.0f);
        barell.setSpeed(barrelSpeed);
        //TODO
    }
}
