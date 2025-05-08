package monke.models.entities;

import monke.models.base.GameEntity;

/**
 * Represents a monkey in the game.
 * Basically just origin coords for barrel spawns.
 */
public class Monkey extends GameEntity{
    public Monkey(double x, double y) {
        super(x, y, new BoundingBox(0, 0, 0, 0));
    }

    @Override
    public void update() {
        //TODO: Throw barrel on cooldown
        //TODO: Animate monkey
        return;
    }
}
