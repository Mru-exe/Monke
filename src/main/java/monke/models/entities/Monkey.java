package monke.models.entities;

import monke.models.base.GameEntity;
import monke.models.common.BoundingBox;

/**
 * Represents a monkey in the game.
 * Basically just origin coords for barrel spawns.
 * TODO
 */
public class Monkey extends GameEntity{
    public Monkey(double x, double y) {
        super(x, y, new BoundingBox(0, 0, 0, 0));
    }

}
