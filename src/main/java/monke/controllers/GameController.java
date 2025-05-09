package monke.controllers;


import javafx.scene.Scene;
import monke.enums.Command;
import monke.enums.GameEvent;
import monke.models.GameLevel;
import monke.models.base.GameEntity;
import monke.models.base.GameObject;
import monke.models.common.Collidable;
import monke.models.common.Updatable;
import monke.models.entities.Player;
import monke.utils.*;
import monke.views.GameView;

import java.util.logging.Logger;

public class GameController {
    private static final Logger logger = Logger.getLogger(GameController.class.getName());
    private final SpriteFactory sf = new SpriteFactory(true);

    protected final InputHandler inputHandler = new InputHandler();

    private final GameLoop logicThread = new GameLoop(){
        @Override
        protected void process(double dt) {
            inputHandler.update();
            resolveMovement();

            for (Updatable u : level.getUpdatable()) {
                u.update(dt);
            }
            for (Collidable c : level.getCollidable()) {
                for (Collidable other : level.getCollidable()) {
                    if (c.hashCode() != other.hashCode() && c.getBounds().intersects(other.getBounds())) {
                        c.onCollision(other);
                    }
                }
            }
        }
    };

    private final GameLevel level;
    private final GameView view;
    private final Player player;

    public GameController(GameLevel level) {
        this.view = new GameView(this);
        logger.finer("GameController initialized");

        this.level = level;
        for (GameObject go : level.getGameObjects()) {
            sf.applySpriteToModel(go);
        }
        this.player = level.getPlayer();

        view.pushSprites(level.getGameObjects());

        new JavaFXInputAdapter(view.getRoot().getScene(), inputHandler);

        view.startRenderingThread();
        this.logicThread.start();
    }

    protected void resolveMovement() {
        if(inputHandler.isPressed(Command.PLAYER_LEFT)) player.applyForceX(-15-player.getDamping());
        if(inputHandler.isPressed(Command.PLAYER_RIGHT)) player.applyForceX(15+player.getDamping());
        if(inputHandler.isPressed(Command.PLAYER_JUMP)) {
            if(Math.abs(player.getVelY()) <= 0) {
                player.applyForceY(-4.5*GameEntity.gravityStrength);
            }
        }
    }

    public Scene getView() {
        return this.view;
    }
}
