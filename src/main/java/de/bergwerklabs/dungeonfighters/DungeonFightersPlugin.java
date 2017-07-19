package de.bergwerklabs.dungeonfighters;

import com.google.gson.GsonBuilder;
import de.bergwerklabs.dungeonfighters.game.config.DungeonFighterConfig;
import de.bergwerklabs.dungeonfighters.game.config.DungeonFighterConfigDeserializer;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighters;
import de.bergwerklabs.dungeonfighters.game.core.arena.fubar.TileType;
import de.bergwerklabs.dungeonfighters.game.core.games.GamesEventHandler;
import de.bergwerklabs.dungeonfighters.game.core.lobby.LobbyEventHandler;
import de.bergwerklabs.dungeonfighters.game.core.lobby.StartHandler;
import de.bergwerklabs.util.GameState;
import de.bergwerklabs.util.GameStateManager;
import de.bergwerklabs.util.LABSGameMode;
import de.bergwerklabs.util.mechanic.StartTimer;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Yannic Rieger on 25.04.2017.
 * <p> DungeonFightersPlugin class for the DungeonFighter minigame. </p>
 * @author Yannic Rieger
 */
public class DungeonFightersPlugin extends LABSGameMode
{
    /**
     * Returns the current instance of the DungeonFightersPlugin class.
     */
    public static DungeonFightersPlugin getInstance() { return instance; }

    /**
     * Gets the DungeonFighter configuration.
     */
    public DungeonFighterConfig getDungeonFighterConfig() { return this.config; }

    public List<File> getThemedGameFolder(String theme) {
        return Arrays.asList(new File(themeFolder + "/" + theme + "/games").listFiles());
    }

    public List<File> getThemedStartPoints(String theme) {
        return Arrays.asList(new File(themeFolder + "/" + theme + "/start_points").listFiles());
    }

    public List<File> getThemedEndPoints(String theme) {
        return Arrays.asList(new File(themeFolder + "/" + theme + "/end_points").listFiles());
    }

    public File getThemedBattleZoneFolder(String theme) {
        return new File(themeFolder + "/" + theme + "/battle_zone");
    }

    public static final DungeonFighters game = new DungeonFighters();
    public static final World arenaWorld = Bukkit.getWorld("arena");
    public static final World moduleWorld = Bukkit.getWorld("module");
    public static final World spawnWorld = Bukkit.getWorld("spawn");

    public static final Listener lobbyListener = new LobbyEventHandler();
    public static final Listener moduleEventHandler = new GamesEventHandler();

    private static DungeonFightersPlugin instance;

    private File configFile = new File(this.getDataFolder() + "/config.json");
    private File menuFolder = new File(this.getDataFolder() + "/menus");
    private File shopFolder = new File(this.getDataFolder() + "/shops");

    private String themeFolder;
    private DungeonFighterConfig config;

    // TODO: put tasks in list an cancle them if needed.

    @Override
    public void labsEnable() {
        instance = this;
        this.themeFolder = this.getDataFolder().getPath() + "/themes";

        this.getGameStateManager().setState(GameState.PREPARING);

        this.createFlatWorld("module");
        this.createFlatWorld("arena");

        this.prepareSpawn(spawnWorld);
        this.prepareWorld(moduleWorld);
        this.prepareWorld(arenaWorld);

        new StartTimer(this, 2, 4, new StartHandler()).launch();

        Bukkit.getPluginManager().registerEvents(lobbyListener, this);

        try {
            this.config = new GsonBuilder().registerTypeAdapter(DungeonFighterConfig.class, new DungeonFighterConfigDeserializer())
                                           .create()
                                           .fromJson(new InputStreamReader(new FileInputStream(this.configFile), Charset.forName("UTF-8")),
                                                     DungeonFighterConfig.class);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        game.determineDungeon();
    }

    @Override
    public void labsDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    @Override
    public GameStateManager.MetadataHandler getMetaDataHandler() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

         /* just for demonstration purposes */
        if (commandLabel.equalsIgnoreCase("money")) {

            Iterator<Location> it = game.getSpawns().iterator();

            Bukkit.getOnlinePlayers().forEach(player -> {
                if (it.hasNext())
                    player.teleport(it.next());
            });
            return true;
        }
        return false;
    }

    @Override
    public String getChatPrefix() {
        return "§6>> §eDungeonFighters §6❘ §7";
    }


    private void prepareSpawn(World world) {
        this.prepareWorld(world);
        world.setPVP(false);
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("spectatorsGenerateChunks","false");
    }

    private void prepareWorld(World world) {
        world.setTime(0);
        world.setStorm(false);
        world.setThundering(false);
        world.setGameRuleValue("doDaylightCycle","false");
    }

    private void createFlatWorld(String name) {
        Bukkit.getServer().createWorld(new WorldCreator(name)
                                               .generateStructures(false)
                                               .environment(World.Environment.NORMAL)
                                               .type(WorldType.FLAT));
    }


    /**
     *
     * @param grid
     * @param mapSize
     */
    private void generateAndSaveImage(TileType[] grid, int mapSize) {

        /*
        try {
            ImageIO.write(Util.createImageFromBoard(grid, mapSize), "png", new File("/home/freggy/laby/spigot_1.8.8/plugins/DungeonFighters/board.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        } */
    }
}
