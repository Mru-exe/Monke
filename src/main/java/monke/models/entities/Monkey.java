package monke.models.entities;

import monke.enums.SpriteImage;
import monke.models.base.GameEntity;
import monke.models.common.BoundingBox;
import monke.utils.SpriteFactory;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Logger;

/**
 * Represents a monkey in the game.
 * Basically just origin coords for barrel spawns.
 * TODO
 */
public class Monkey extends GameEntity{
    private static final Logger logger = Logger.getLogger(Monkey.class.getName());
    private Set<Barrel> barrels = new CopyOnWriteArraySet<>();

    private SpriteFactory sf = new SpriteFactory();

    private double cooldown = 10d;

    public Monkey(double x, double y, Set<Barrel> barrelSet) {
        super(x, y, new BoundingBox(x, y, 64, 64));
        this.img = SpriteImage.MONKEY;
        this.barrels = barrelSet;
    }

    private void spawnBarrel() {
        Barrel barrel = new Barrel((int) x-64, (int) y);
        sf.applySpriteToModel(barrel);
        barrels.add(barrel);
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        cooldown -= dt;
        if(cooldown <= 0){
            spawnBarrel();
            cooldown = 10d;
        }
    }

    @Override
    public void resolveHorizontalCollision(double dx, double overlapX) {
        double shiftX = dx > 0 ? overlapX : -overlapX;
        this.setCoords(this.getX() + shiftX, this.getY());
        this.applyForceX(0);
    }

    @Override
    public void resolveVerticalCollision(double dy, double overlapY) {
        if(dy > 0){
            this.applyForceY(gravityStrength);
        } else {
            this.setCoords(this.getX(), this.getY() - overlapY);
            this.applyForceY(0);
        }
    }
}
