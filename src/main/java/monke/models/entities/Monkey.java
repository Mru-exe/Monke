package monke.models.entities;

import monke.models.base.GameEntity;

/**
 * Represents a monkey in the game.
 * Basically just origin coords for barrel spawns.
 */
public class Monkey extends GameEntity{
    public Monkey(float x, float y) {
        super(x, y);
    }

    public void throwBarrel(int barrelSpeed) {
        Barrel barell = new Barrel(this.getX(), this.getY());
        barell.setSpeed(barrelSpeed);
        //TODO
    }

    @Override
    public void update() {
        //TODO: Throw barrel on cooldown
        //TODO: Animate monkey
        return;
    }
}
