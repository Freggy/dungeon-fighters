package de.bergwerklabs.dungeonfighters;

import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.game.config.DungeonFighterConfig;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighters;
import de.bergwerklabs.dungeonfighters.game.core.arena.fubar.TileType;
import de.bergwerklabs.dungeonfighters.game.core.arena.map.DungeonArenaLoader;
import de.bergwerklabs.dungeonfighters.game.core.games.GamesEventHandler;
import de.bergwerklabs.dungeonfighters.game.core.games.map.DungeonGameLoader;
import de.bergwerklabs.dungeonfighters.util.Util;
import de.bergwerklabs.framework.commons.spigot.scoreboard.LabsScoreboard;
import de.bergwerklabs.util.GameStateManager;
import de.bergwerklabs.util.LABSGameMode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
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
     * Gets the current scoreboard template.
     */
    public LabsScoreboard getScoreboard() { return this.scoreboard; }

    /**
     * Gets the DungeonFighter configuration.
     */
    public DungeonFighterConfig getDungeonFighterConfig() { return this.config; }

    /**
     *
     */
    public List<BukkitTask> getTasks() { return this.tasks; }

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

    public final static DungeonFighters game = new DungeonFighters();

    private static DungeonFightersPlugin instance;

    private File configFile = new File(this.getDataFolder() + "/config.json");
    private File menuFolder = new File(this.getDataFolder() + "/menus");
    private File shopFolder = new File(this.getDataFolder() + "/shops");

    private String themeFolder;

    private LabsScoreboard scoreboard;

    private DungeonFighterConfig config;
    private DungeonArenaLoader loader;

    private List<BukkitTask> tasks = new ArrayList<>();

    // TODO: put tasks in list an cancle them if needed.

    @Override
    public void labsEnable() {
        instance = this;
        this.loader = new DungeonArenaLoader();
        this.themeFolder = this.getDataFolder().getPath() + "/themes";

        //this.getServer().getPluginManager().registerEvents(new DeathmatchEventHandlers(), this);
        this.getServer().getPluginManager().registerEvents(new GamesEventHandler(), this);

        DungeonGameLoader loader = new DungeonGameLoader();
        loader.buildDungeons(DungeonFightersPlugin.game.determineDungeon(), null);

        Bukkit.getScheduler().runTaskTimer(DungeonFightersPlugin.getInstance(), () -> {
            DungeonFightersPlugin.game.getPlayerManager().getPlayers().values().forEach(fighter -> {

                String chunkCoordinates = Util.getChunkCoordinateString(fighter.getPlayer().getLocation().getChunk());

                DungeonMechanicProvider gameToPlay = DungeonFightersPlugin.game.getDungeon().getGamePositions().get(chunkCoordinates);
                DungeonMechanicProvider currentGame = fighter.getSession().getCurrentGame();

                if (gameToPlay != null) {
                    if (gameToPlay.getId().contains("built-in") && currentGame.getChunks().contains(chunkCoordinates)) {
                        this.initGame(fighter, gameToPlay, chunkCoordinates);
                    }
                    else if (!currentGame.getId().equals(gameToPlay.getId())) {
                        this.initGame(fighter, gameToPlay, chunkCoordinates);
                    }
                    else if (currentGame.getChunks().contains(chunkCoordinates)) {
                        this.close(fighter.getPlayer(), chunkCoordinates);
                        currentGame.reset();
                        currentGame.getChunks().remove(chunkCoordinates);
                    }
                }
            });
        }, 0, 20L);

        /*
        try {
            this.config = new GsonBuilder().registerTypeAdapter(DungeonFighterConfig.class, new DungeonFighterConfigDeserializer()).create()
                                           .fromJson(new InputStreamReader(new FileInputStream(configFile), Charset.forName("UTF-8")), DungeonFighterConfig.class);

            InventoryMenuFactory.readMenus(menuFolder, null);

            ShopFactory.readNPCShops(shopFolder);
            NPCShopManager.getShops().values().forEach(shop -> shop.spawnShop());

            scoreboard = LabsScoreboardFactory.createInstance(this.getDataFolder() + "/scoreboard.json");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new ArrowTrailTask(), 0, 0);

        //this.loader.generate(this.config.getGridOrigin(), this.determineDungeon());
        */
    }

    private void initGame(DungeonFighter fighter, DungeonMechanicProvider provider, String chunkCoord) {
        fighter.getSession().getCurrentGame().stop();
        this.close(fighter.getPlayer(), chunkCoord);
        fighter.getSession().setCurrentGame(provider);
        fighter.getSession().getCurrentGame().getChunks().remove(chunkCoord);
        fighter.getSession().getCurrentGame().assignPlayer(fighter);
        fighter.getSession().getCurrentGame().assignModule(game.getModules().get(chunkCoord).getSchematic());
        fighter.getSession().getCurrentGame().start();
    }

    private void close(Player player, String  coords) {
        List<Location> blocks = game.getModules().get(coords).getBlockLocations();
        Util.closeEntrance(player, blocks);
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
