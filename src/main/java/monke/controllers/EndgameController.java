package monke.controllers;

import javafx.fxml.FXML;
import monke.enums.GameEvent;
import monke.utils.EventBus;
import monke.views.EndgameView;

/**
 * Controller class for EndgameView.
 * @see EndgameView
 */
public class EndgameController {
    EndgameView view;

    public enum EndgameType {
        WIN,
        LOSE
    }

    /**
     * Constructs an EndgameController instance, initializing the endgame view.
     *
     * @param type instance of EndgameType representing the endgame type (WIN or LOSE) and determines the message displayed.
     * @see EndgameType
     */
    public EndgameController(EndgameType type) {
        this.view = new EndgameView(this);
        switch (type){
            case WIN -> {
                view.setLabelMessage("YOU WIN!");
                view.hideNodeById("restart");
            }
            case LOSE -> view.setLabelMessage("GAME OVER!");
        }
    }

    public EndgameView getView() {
        return view;
    }

    /**
     * Returns player to the main menu.
     */
    @FXML
    public void exit(){
        EventBus.publish(GameEvent.OPEN_MAIN_MENU);
    }

    /**
     * Restarts the game.
     */
    @FXML
    public void restart(){
        EventBus.publish(GameEvent.START_GAME);
    }
}
