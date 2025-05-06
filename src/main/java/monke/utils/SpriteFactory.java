package monke.utils;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
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
    public void applySpriteToModel(GameObject go){
        logger.finer("Creating sprite from model: " + go.getId());
        Collection<Node> el = new ArrayList<>();

        Group root = new Group();

        if(showDebug){
            //If go is of type GameEntity - get bounds
            if(go instanceof Collidable){
                BoundingBox bounds = ((Collidable) go).getBounds();
                Rectangle rect = new Rectangle(0, 0, bounds.getWidth(), bounds.getHeight());
                rect.setFill(null);
                rect.setStroke(Color.RED);
                rect.setStrokeWidth(3);
                el.add(rect);
            }

            Text coords = new Text();
            StringBinding binding = Bindings.createStringBinding(
                    () -> String.format(go.getId() + "\n[%.1f,%.1f]", root.getTranslateX(), root.getTranslateY()),
                    root.translateXProperty(),
                    root.translateYProperty()
            );
            coords.textProperty().bind(binding);
            el.add(coords);
        }

        root.getChildren().addAll(el);
        root.setStyle("-fx-background-color: #ffffff;");
        root.setTranslateX(go.getX());
        root.setTranslateY(go.getY());
        go.setFxSprite(root);
    }
}
