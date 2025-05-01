package monke.utils;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import monke.models.base.GameEntity;
import monke.models.base.GameObject;
import monke.models.common.BoundingBox;

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
    public void applySpriteToModel(GameObject go){
        logger.finer("Creating sprite from model: " + go.getDebugString());
        Collection<Node> el = new ArrayList<>();

        if(showDebug){
            //If go is of type GameEntity - get bounds
            if(go instanceof GameEntity){
                BoundingBox bounds = ((GameEntity) go).getBounds();
                Rectangle rect = new Rectangle(0, 0, bounds.getWidth(), bounds.getHeight());
                rect.setFill(null);
                rect.setStroke(Color.RED);
                rect.setStrokeWidth(3);
                el.add(rect);
            }

            Text text = new Text(0, 0, go.getDebugString() + "\n" + go.getX() + "," + go.getY());
            el.add(text);
        }

        Group root = new Group(el);
        root.setStyle("-fx-background-color: #ffffff;");
        root.setTranslateX(go.getX());
        root.setTranslateY(go.getY());
        go.setFxSprite(root);
    }
}
