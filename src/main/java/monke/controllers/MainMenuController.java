package monke.controllers;

import javafx.fxml.FXML;
import monke.enums.GameEvent;
import monke.utils.EventBus;
import monke.views.BaseView;
import monke.views.MainMenu;


public class MainMenuController {
    MainMenu view;

    public MainMenuController(){
        this.view = new MainMenu(this);
    }

    public BaseView getView(){
        return this.view;
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
