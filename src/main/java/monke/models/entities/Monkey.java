package monke.models.entities;

import monke.enums.SpriteImage;
import monke.models.base.GameEntity;
import monke.models.common.BoundingBox;
import monke.utils.SpriteFactory;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

/**
 * Represents a monkey in the game.
 * Basically just origin coords for barrel spawns.
 * TODO
 */
public class Monkey extends GameEntity{
    private static final Logger logger = Logger.getLogger(Monkey.class.getName());
    private final CopyOnWriteArraySet<Barrel> barrels;

    private final SpriteFactory sf = new SpriteFactory();

    private final int direction;

    private double currentCooldown;
    private final double cooldown;
    private int specialCounter = 3;

    public Monkey(double x, double y, CopyOnWriteArraySet<Barrel> barrelSet, double cooldown) {
        super(x, y, new BoundingBox(x, y, 64, 64));
        this.cooldown = cooldown;
        this.direction = x > 500 ? -1 : 1;
        this.img = SpriteImage.MONKEY;
        this.barrels = barrelSet;
    }

    private void spawnBarrel() {
        specialCounter--;
        Barrel barrel = new Barrel((int) x, (int) y, direction*7);
        if(specialCounter <= 0){
//            barrel.setDamping(1.01f);
            specialCounter = 3;
            barrel.setSpeed(2.5f*barrel.getSpeed());
        }
        sf.applySpriteToModel(barrel);
        barrels.add(barrel);
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        currentCooldown -= dt;
        if(currentCooldown <= 0){
            spawnBarrel();
            currentCooldown = cooldown;
        }
    }

    @Override
    public void resolveVerticalCollision(double dy, double overlapY) {
        if(dy > 0){
            this.setVelY(gravityStrength);
        } else {
            this.setCoords(this.getX(), this.getY() - overlapY);
            this.setVelY(0);
        }
    }
}
