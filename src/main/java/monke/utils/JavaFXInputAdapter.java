package monke.utils;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import monke.enums.Command;

import java.util.Map;

/**
 * JavaFX bridge: glues Scene key events to the core InputHandler.
 * Includes key-commands bindings.
 */
public class JavaFXInputAdapter {
    private final Map<KeyCode,Command> bindings = Map.ofEntries(
            Map.entry(KeyCode.A,      Command.PLAYER_LEFT),
            Map.entry(KeyCode.LEFT,   Command.PLAYER_LEFT),
            Map.entry(KeyCode.D,      Command.PLAYER_RIGHT),
            Map.entry(KeyCode.RIGHT,  Command.PLAYER_RIGHT),
            Map.entry(KeyCode.SPACE,  Command.PLAYER_JUMP),
            Map.entry(KeyCode.UP,     Command.PLAYER_JUMP),
            Map.entry(KeyCode.ESCAPE, Command.TOGGLE_PAUSE)
    );

    /**
     * Constructor for JavaFXInputAdapter.
     * Sets up key event handlers for the given scene.
     *
     * @param scene The JavaFX Scene to attach input handlers to.
     * @param core  The InputHandler instance to handle input commands.
     */
    public JavaFXInputAdapter(Scene scene, InputHandler core) {
        scene.setOnKeyPressed(e -> {
            Command cmd = bindings.get(e.getCode());
            if (cmd != null){
                core.press(cmd);
            }
        });
        scene.setOnKeyReleased(e -> {
            Command cmd = bindings.get(e.getCode());
            if (cmd != null) core.release(cmd);
        });
    }
}
