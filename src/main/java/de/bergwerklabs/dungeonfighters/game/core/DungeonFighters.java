package de.bergwerklabs.dungeonfighters.game.core;

import de.bergwerklabs.dungeonfighters.DungeonPlugin;
import de.bergwerklabs.dungeonfighters.game.core.games.map.DungeonGameWrapper;
import de.bergwerklabs.framework.commons.spigot.game.PlayerManager;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Yannic Rieger on 01.05.2017.
 * <p>  </p>
 * @author Yannic Rieger
 */
public class DungeonFighters {

    /**
     *
     */
    public PlayerManager<DungeonFighter> getPlayerManager() {
        return playerManager;
    }

    /**
     *
     */
    public Queue<DungeonGameWrapper> getGames() { return this.games; }

    public Dungeon getDungeon() {
        return dungeon;
    }

    private static DungeonFighters instance;
    private PlayerManager<DungeonFighter> playerManager = new PlayerManager<>();
    private Queue<DungeonGameWrapper> games = new LinkedList<>();
    private Dungeon dungeon;

    public DungeonFighters() {
        if (instance != null) return;
        instance = this;
    }

    /**
     * Randomly determines the map that will be played
     *
     * @return Folder containing the module schematics.
     */
    public Dungeon determineDungeon() {
        //File[] maps = new File(this.getDataFolder() + "/maps").listFiles();

        //TileType[] grid = Generator.generateMap(10); // TODO: make confiurable
        //this.generateAndSaveImage(grid, 10);
        //SecureRandom random = new SecureRandom();

        return this.dungeon = new Dungeon(DungeonPlugin.getInstance().getThemedGameFolder("temple"),
                                          DungeonPlugin.getInstance().getThemedStartPoints("temple"),
                                          DungeonPlugin.getInstance().getThemedEndPoints("temple"), null);
    }
}
