package de.bergwerklabs.dungeonfighters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.bergwerklabs.dungeonfighters.game.config.DungeonFighterConfig;
import de.bergwerklabs.dungeonfighters.game.config.DungeonFighterConfigDeserializer;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighters;
import de.bergwerklabs.dungeonfighters.game.core.arena.fubar.TileType;
import de.bergwerklabs.dungeonfighters.game.core.games.GamesEventHandler;
import de.bergwerklabs.dungeonfighters.game.core.lobby.LobbyEventHandler;
import de.bergwerklabs.dungeonfighters.game.core.lobby.StartHandler;
import de.bergwerklabs.dungeonfighters.util.animation.TitleAnimation;
import de.bergwerklabs.dungeonfighters.util.animation.TitleAnimationDeserializer;
import de.bergwerklabs.framework.commons.spigot.entity.npc.GlobalNpc;
import de.bergwerklabs.framework.commons.spigot.entity.npc.PlayerSkin;
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
    public static World arenaWorld;
    public static World moduleWorld;
    public static World spawnWorld;
    public static GlobalNpc npc;

    public static final Listener lobbyListener = new LobbyEventHandler();
    public static final Listener moduleEventHandler = new GamesEventHandler();

    public static TitleAnimation animation;

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
        animation = this.readTitleAnimation();

        arenaWorld = this.createFlatWorld("arena");
        moduleWorld = this.createFlatWorld("module");
        spawnWorld = Bukkit.getWorld("spawn");

        this.getGameStateManager().setState(GameState.PREPARING);

        this.prepareSpawn(spawnWorld);
        this.prepareWorld(moduleWorld);
        this.prepareWorld(arenaWorld);

        Location npcSpawn = new Location(spawnWorld, 34.5, 95, 101.5);
        npcSpawn.setPitch(0);
        npcSpawn.setYaw(-171);

        npc = new GlobalNpc(npcSpawn, getSkin(), "Itemhändler", "Gib einfach Coins aus");
        npc.spawn();

        new StartTimer(this, 1, 4, new StartHandler()).launch();

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

    private TitleAnimation readTitleAnimation() {
        Gson gson = new GsonBuilder().registerTypeAdapter(TitleAnimation.class, new TitleAnimationDeserializer())
                                     .create();
        try {
            return gson.fromJson(new InputStreamReader(new FileInputStream(this.getDataFolder() + "/animation.json"),
                                                                    Charset.forName("UTF-8")), TitleAnimation.class);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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

    private World createFlatWorld(String name) {
        return Bukkit.getServer().createWorld(new WorldCreator(name)
                                                      .generateStructures(false)
                                                      .environment(World.Environment.NORMAL)
                                                      .type(WorldType.FLAT));
    }


    private PlayerSkin getSkin() {
        return new PlayerSkin("eyJ0aW1lc3RhbXAiOjE1MDA4MTgzNzc2NjksInByb2ZpbGVJZCI6IjQ4N2RiNmNmZWViMzRhMjE4MjM3YzAzNDhhMjg1NjY2IiwicHJvZmlsZU5hbWUiOiJhdXNkZXJmdXR1cmUiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2VhNWI4MmU0NDNmY2FlZTdiZGUxZjkzNmQ1ZWJlNTg1NThhZTAzMjFkNDg5ODVkZGZlODRiOTUyMWU3OTg5YSJ9fX0=",
                              "SrFsjVwK8Aes1+k9QLzqlFwjlKeyZ48h9PpdnLW296Z9R68AOsJIOrj+jEiEtfSMe8CGlQ/VoureALlUSA9YJuZyEWM6i4Y28Q2IWGQr9Firq6yPho6sjkBEkJirbIEHtBKiSlPc4ts/yVelMkGtg5yDxsI201aJFVDi2gNKDdjVf2DNT5RhCtue7eCXjNstPZPbK9sHZ+M9jkUQu6RmPdNSfRBGBn2+FnDlUutd1aYIVZLO2Oue3i93TtrDmH7gNlFybPXVM+2U0xyoBEva0sIxUktTZhTiZ24v9IOkXffgV1R2VVVggp2AZISWIdl3CuYhFVZefca3SEAeRemC6LBw9PEVmQckexSFyw4llIlRKfA6/7XOsFXGdpyFStMF3sEkfXoX2R93bDugYhyk9/EJluCV5owWjQjvkAfOazhcWu5dZfTtksdcF+xjKdecLIGY6lL1HVpjVyqGl9ZOYShq+zD7bhBI+0jPkzyytrr4vis7XKcADr5USYFW6gvkARF75zBUfyUXbn4r9PF9IyPS01/uiXQVCqFop69H5vHBs892xdYNtaEdOoAcSLZIlSwYcN+pZndwZAN/MCsb8eRS4lPp5Ehm92+MU+ExM4ZtgBoNPPwisf+zq0/KnvcOrNl+8yuDVyWhu8q1YjIGM2xPQzIw93zzzawuQauPxmw=");
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
