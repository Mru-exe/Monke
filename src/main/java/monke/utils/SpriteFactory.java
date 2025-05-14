package monke.utils;

import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import monke.enums.SpriteImage;
import monke.models.Platform;
import monke.models.base.GameEntity;
import monke.models.base.GameObject;
import monke.models.common.BoundingBox;
import monke.models.common.Collidable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * This class is responsible for creating sprites and serving them to views.
 * @implNote JavaFX exclusive.
 */
public class SpriteFactory {
    private static final Logger logger = Logger.getLogger(SpriteFactory.class.getName());

    private final boolean showDebug;

    private final ConcurrentHashMap<SpriteImage, Image> imageCache = new ConcurrentHashMap<>();

    /**
     * @param isTest - false by default. set to true if you want to render hitboxes.
     */
    public SpriteFactory(boolean isTest) {
        this.showDebug = isTest;
        this.loadImages();
    }
    public SpriteFactory() {
        this.showDebug = false;
        this.loadImages();
    }

    private void loadImages(){
        for(SpriteImage image : SpriteImage.values()){
            logger.finer("Looking sprite: " + image);
            if(!imageCache.containsKey(image) && image.getPath() != null){
                try(InputStream stream = this.getClass().getResourceAsStream(image.getPath())) {
                    Image img = new Image(stream);
                    imageCache.put(image, img);
                    logger.info(image + " sprite successfully cached");
                } catch (Exception e){
                    logger.warning("Failed to load " + image + " sprite");
//                    e.printStackTrace();
                }
            }
        }
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
        root.setAutoSizeChildren(true);


        Rectangle rect = new Rectangle(0, 0, go.getImg().getWidth(), go.getImg().getHeight());
        if (go instanceof Collidable collidable) {
            BoundingBox bounds = collidable.getBounds();
            rect = new Rectangle(0, 0, bounds.getWidth(), bounds.getHeight()); //adjust to hitbox
            root.getChildren().add(rect);
        }
        rect.setFill(null);


        if (showDebug) {
            rect.setStroke(Color.RED);
            rect.setStrokeWidth(1);

            Collection<Node> debugElements = new ArrayList<>();
            try {

                if (go instanceof GameEntity entity) {
                    Text coords = new Text();
                    coords.textProperty().bind(Bindings.createStringBinding(
                            () -> String.format("%s\n[%.1f, %.1f]\nVel: [%.3f, %.3f]",
                                    go,
                                    root.getTranslateX(), root.getTranslateY(),
                                    entity.getVelX(), entity.getVelY()
                            ),
                            root.translateXProperty(), root.translateYProperty()
                    ));
                    debugElements.add(coords);
                } else {
                    Text coords = new Text();
                    coords.textProperty().bind(Bindings.createStringBinding(
                            () -> String.format("%s\n[%.1f, %.1f]",
                                    go,
                                    root.getTranslateX(), root.getTranslateY()
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

        if(go instanceof Platform){
            Image tileImg = imageCache.get(go.getImg());
            rect.setFill(new ImagePattern(tileImg, 0, 0,
                    tileImg.getWidth(),
                    tileImg.getHeight(),
                    false));
        } else {
            ImageView img =  new ImageView(imageCache.get(go.getImg()));
            img.setId("image");
            img.setFitHeight(go.getImg().getHeight());
            img.setFitWidth(go.getImg().getWidth());

            if(go.getImg() == SpriteImage.BARREL){
                img.rotateProperty().bind(root.translateXProperty().multiply(1.5f));
            }
            root.getChildren().add(img);
        }

        go.setFxSprite(root);
    }
}
