package monke.models;

import monke.models.entities.Monkey;
import monke.models.entities.Player; 

import java.util.UUID;

public class Level {
    private String name;
    private UUID levelId;

    // private int guiScale;
    // private int width;
    // private int height;
    
    private int spawnX;
    private int spawnY;

    private Player player;
    private Monkey monkey;
    private Goal goal;

    private Platform[] platforms;


    public Level(String name, int spawnX, int spawnY) {
        this.name = name;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.levelId = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return levelId;
    }

    public void spawnPlayer(Player player) {
        this.player = player;
        player.setCoords(spawnX, spawnY);
    }

    public void setMonkey(Monkey monkey) {
        this.monkey = monkey;
    }

    public void setPlatforms(Platform[] platforms) {
        this.platforms = platforms;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}