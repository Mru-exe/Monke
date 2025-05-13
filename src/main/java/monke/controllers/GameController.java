package monke.controllers;


import javafx.application.Platform;
import javafx.scene.Scene;
import monke.enums.Command;
import monke.enums.GameEvent;
import monke.models.GameLevel;
import monke.models.base.GameEntity;
import monke.models.base.GameObject;
import monke.models.common.Collidable;
import monke.models.common.Updatable;
import monke.models.entities.Barrel;
import monke.models.entities.GoalKey;
import monke.utils.*;
import monke.views.GameView;

import java.util.logging.Logger;

public class GameController {
    private static final Logger logger = Logger.getLogger(GameController.class.getName());
    private final SpriteFactory sf = new SpriteFactory(false);

    protected final InputHandler inputHandler = new InputHandler();

    private final GameLoop logicThread = new GameLoop(){
        @Override
        protected void process(double dt) {
            inputHandler.update();
            resolveControls();

            //Update all updatable entities
            for (Updatable u : level.getUpdatable()) {
                u.update(dt);
            }

            //Check for collisions
            checkCollisions();

            if(level.getPlayer().overlaps(level.getGoal())) level.getGoal().unlock(level.getPlayer());
        }
    };

    private final GameLevel level;
    private final GameView view;

    public GameController(GameLevel level) {
        this.view = new GameView(this);
        logger.finer("GameController initialized");

        this.level = level;
        for (GameObject go : level.getGameObjects()) {
            sf.applySpriteToModel(go);
        }

        view.initSprites(level.getGameObjects());

        new JavaFXInputAdapter(view.getRoot().getScene(), inputHandler);

        EventBus.subscribe(GameEvent.TOGGLE_PAUSE, () -> {
            logger.finer("Toggling pause");
            if (logicThread.isPaused()) {
                logicThread.unpause();
            } else {
                logicThread.pause();
            }
        });

        EventBus.subscribe(GameEvent.DIE, () -> {
            logicThread.stop();
            view.stopRenderingThread();
            Platform.runLater( () -> EventBus.publish(GameEvent.OPEN_MAIN_MENU));
            System.out.println("YOU LOSE");
        });
        EventBus.subscribe(GameEvent.WIN, () -> {
            if(logicThread.isRunning()){
                logicThread.stop();
                view.stopRenderingThread();
                Platform.runLater( () -> EventBus.publish(GameEvent.OPEN_MAIN_MENU));
                System.out.println("YOU WIN");
            }
        });

        this.logicThread.start();
        view.startRenderingThread();
    }

    protected void resolveControls() {
        if(inputHandler.isPressed(Command.PLAYER_LEFT)) level.getPlayer().applyForceX(-level.moveSpeed-level.getPlayer().getDamping());
        if(inputHandler.isPressed(Command.PLAYER_RIGHT)) level.getPlayer().applyForceX(level.moveSpeed+level.getPlayer().getDamping());
        if(inputHandler.isPressed(Command.PLAYER_LEFT) && inputHandler.isPressed(Command.PLAYER_RIGHT)) level.getPlayer().applyForceX(0);
        if(inputHandler.isPressed(Command.PLAYER_JUMP)) {
            if(Math.abs(level.getPlayer().getVelY()) <= 0) {
                level.getPlayer().applyForceY(-level.jumpStrength*GameEntity.gravityStrength);
            }
        }
    }

    private void checkCollisions(){
        //Surface collisions
        for (Collidable c : level.getCollidable()) {
            for (Collidable other : (level.getPlatforms())) {
                if(c == null || other == null) continue;
                if (c.hashCode() != other.hashCode() && c.overlaps(other)) {
                    c.resolveCollision(other);
                }
            }
        }
        //Key collisions
        for(GoalKey key : level.getItems()){
            if(level.getPlayer().overlaps(key)){
                logger.info("Player collided with item");
                level.getPlayer().pickupKey(key);
                view.removeSprite(key);
                level.destroyObject(key);
            }
        }
        //Player/Barrel collisions
        for(Barrel barrel : level.getBarrels()){
            if(level.getPlayer().overlaps(barrel)){
                EventBus.publish(GameEvent.DIE);
            }
        }
    }

    public Scene getView() {
        return this.view;
    }

    public GameLevel getLevel() {
        return level;
    }
}
