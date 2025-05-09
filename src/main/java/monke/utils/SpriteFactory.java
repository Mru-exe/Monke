package monke.utils;

import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import monke.models.base.GameEntity;
import monke.models.base.GameObject;
import monke.models.common.BoundingBox;
import monke.models.common.Collidable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * This class is responsible for creating sprites and serving them to views.
 * @implNote JavaFX exclusive.
 */
public class SpriteFactory {
    private static Logger logger = Logger.getLogger(SpriteFactory.class.getName());

    private final boolean showDebug;

    /**
     * @param isTest - false by default. set to true if you want to render hitboxes.
     */
    public SpriteFactory(boolean isTest) {
        this.showDebug = isTest;
    }
    public SpriteFactory() {
        this.showDebug = false;
    }

    /**
     * Creates a Sprite entity from a model object, using JavaFX group class;
     * @param go model (GameObject instance)
     * @return Sprite object
     */
    public void applySpriteToModel(GameObject go) {
        logger.finer("Creating sprite from model: " + go.toString());

        Group root = new Group();
        root.setTranslateX(go.getX());
        root.setTranslateY(go.getY());
        root.setStyle("-fx-background-color: #ffffff;");

        if (showDebug) {
            Collection<Node> debugElements = new ArrayList<>();
            try {
                if (go instanceof Collidable collidable) {
                    BoundingBox bounds = collidable.getBounds();
                    Rectangle rect = new Rectangle(0, 0, bounds.getWidth(), bounds.getHeight());
                    rect.setFill(null);
                    rect.setStroke(Color.RED);
                    rect.setStrokeWidth(3);
                    debugElements.add(rect);
                }

                if (go instanceof GameEntity entity) {
                    Text coords = new Text();
                    coords.textProperty().bind(Bindings.createStringBinding(
                            () -> String.format("%s\n[%.1f, %.1f]\nVel: [%.3f, %.3f]",
                                    go.toString(),
                                    root.getTranslateX(), root.getTranslateY(),
                                    entity.getVelX(), entity.getVelY()
                            ),
                            root.translateXProperty(), root.translateYProperty()
                    ));
                    debugElements.add(coords);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            root.getChildren().addAll(debugElements);
        }

        go.setFxSprite(root);
    }
}
