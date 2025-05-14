package monke.controllers;

import javafx.fxml.FXML;
import monke.enums.GameEvent;
import monke.utils.EventBus;
import monke.views.EndgameView;

public class EndgameController {
    EndgameView view;

    public enum EndgameType {
        WIN,
        LOSE
    }

    public EndgameController(EndgameType type) {
        this.view = new EndgameView(this);
        switch (type){
            case WIN -> {
                view.setLabelMessage("YOU WIN!"); // TODO nefunguje
                view.hideNodeById("restart");
            }
            case LOSE -> {
                view.setLabelMessage("GAME OVER!");
            }
        }
    }

    public EndgameView getView() {
        return view;
    }

    @FXML
    public void exit(){
        EventBus.publish(GameEvent.OPEN_MAIN_MENU);
    }

    @FXML
    public void restart(){
        EventBus.publish(GameEvent.START_GAME);
    }
}
