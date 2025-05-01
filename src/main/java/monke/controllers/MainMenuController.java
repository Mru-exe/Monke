package monke.controllers;

import javafx.fxml.FXML;
import monke.enums.GameEvent;
import monke.utils.EventBus;


public class MainMenuController {
    public MainMenuController(){
        //init
    }

    @FXML
    public void exitGame(){
        EventBus.publish(GameEvent.EXIT_GAME);
    }

    @FXML
    public void startGame(){
        EventBus.publish(GameEvent.START_GAME);
    }

    @FXML
    public void selectLevel(){

    }
}
