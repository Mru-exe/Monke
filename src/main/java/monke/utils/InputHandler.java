package monke.utils;


import monke.enums.Command;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * InputHandler is responsible for handling user input events.
 * (TBD) Raises event on key presses and mouse clicks
 */
public abstract class InputHandler {
    private static Logger logger = Logger.getLogger(InputHandler.class.getName());

    public static final ConcurrentHashMap<String, Command> keyBindings = new ConcurrentHashMap<>();

    static {
        keyBindings.put("A", Command.PLAYER_LEFT);
        keyBindings.put("LEFT", Command.PLAYER_LEFT);
        keyBindings.put("D", Command.PLAYER_RIGHT);
        keyBindings.put("RIGHT", Command.PLAYER_RIGHT);
        keyBindings.put("SPACE", Command.PLAYER_JUMP);
        keyBindings.put("UP", Command.PLAYER_JUMP);
        keyBindings.put("ESCAPE", Command.GAME_QUIT);
    }

    public static Command parse(String keyCode, boolean isReleased){
        logger.finest("Parsing keyCode: " + keyCode + " isReleased: " + isReleased);
        if(isReleased &&
                (keyBindings.get(keyCode) == Command.PLAYER_LEFT ||
                keyBindings.get(keyCode) == Command.PLAYER_RIGHT)) {
            return Command.PLAYER_STOP;
        } else if (!isReleased && keyBindings.containsKey(keyCode)) {
            return keyBindings.get(keyCode);
        } else {
            return Command.UNBOUND;
        }
    }

    public static void bind(String key, Command command) {
        keyBindings.put(key, command);
    }
}

