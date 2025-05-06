package monke.models.entities;

import monke.enums.PlayerState;
import monke.models.base.GameEntity;
import monke.models.common.BoundingBox;
import monke.models.common.Collidable;

/**
 * Represents a player in the game.
 */
public class Player extends GameEntity{

    private PlayerState state;
    private final float jumpStrength = 8;
    private final float moveSpeed = 5;

    public Player(int x, int y, int boundaryWidth, int boundaryHeight) {
        super(x, y, new BoundingBox(x, y, boundaryWidth, boundaryHeight));
    }

    public synchronized void jump() {
        if(this.state == PlayerState.IDLE || this.state == PlayerState.FALLING){
            setVelY(jumpStrength);
        }
    }

    public synchronized void moveLeft(){
        if(this.state != PlayerState.CLIMBING){
            setVelX(moveSpeed*-1);
        }
    }

    public synchronized void moveRight(){
        if(this.state != PlayerState.CLIMBING){
            setVelX(moveSpeed);
        }
    }

    public void setState(PlayerState state) {
        this.state = state;
    }
}