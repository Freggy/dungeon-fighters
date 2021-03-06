package de.bergwerklabs.dungeonfighters.game.core;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.DungeonPath;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.generation.DungeonPathLoader;
import de.bergwerklabs.framework.commons.spigot.game.PlayerManager;

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

    public DungeonPath getPath() {
        return path;
    }

    public String getTheme() {
        return theme;
    }

    private static DungeonFighters instance;
    private PlayerManager<DungeonFighter> playerManager = new PlayerManager<>();
    private DungeonPath path;
    private String theme;

    /**
     *
     */
    public DungeonFighters() {
        if (instance == null) {
            instance = this;
        }
        this.theme = "temple"; // TODO: determine randomly
    }

    /**
     *
     */
    public void buildDungeonPath() {
        this.path = new DungeonPathLoader().buildDungeons(this.determineDungeon(), "temple"); // TODO: determine theme randomly
    }

    /**
     * Randomly determines the map that will be played
     *
     * @return Folder containing the module schematics.
     */
    private Dungeon determineDungeon() {
        //File[] maps = new File(this.getDataFolder() + "/maps").listFiles();

        //TileType[] grid = Generator.generateMap(10); // TODO: make confiurable
        //this.generateAndSaveImage(grid, 10);
        //SecureRandom random = new SecureRandom();

        return new Dungeon(DungeonFightersPlugin.getInstance().getThemedGameFolder(this.theme),
                           DungeonFightersPlugin.getInstance().getThemedStartPoints(this.theme),
                           DungeonFightersPlugin.getInstance().getThemedEndPoints(this.theme), null);
    }
}
