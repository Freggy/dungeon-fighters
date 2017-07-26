package de.bergwerklabs.dungeonfighters.game.core;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.game.core.games.map.ModuleInfo;
import de.bergwerklabs.framework.commons.spigot.game.PlayerManager;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public Dungeon getDungeon() {
        return dungeon;
    }

    public List<Location> getSpawns() { return this.spawns; }

    public HashMap<String, ModuleInfo> getModules() { return this.modules; }

    private static DungeonFighters instance;
    private PlayerManager<DungeonFighter> playerManager = new PlayerManager<>();
    private HashMap<String, ModuleInfo> modules = new HashMap<>();
    private List<Location> spawns = new ArrayList<>();
    private Dungeon dungeon;

    public DungeonFighters() {
        if (instance == null) {
            instance = this;
        }
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

        return this.dungeon = new Dungeon(DungeonFightersPlugin.getInstance().getThemedGameFolder("temple"),
                                          DungeonFightersPlugin.getInstance().getThemedStartPoints("temple"),
                                          DungeonFightersPlugin.getInstance().getThemedEndPoints("temple"), null);
    }
}