package de.bergwerklabs.dungeonfighters;

import com.google.gson.GsonBuilder;
import de.bergwerklabs.dungeonfighters.game.config.DungeonFighterConfig;
import de.bergwerklabs.dungeonfighters.game.config.DungeonFighterConfigDeserializer;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighters;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFightersEventHandler;
import de.bergwerklabs.dungeonfighters.game.core.fubar.Generator;
import de.bergwerklabs.dungeonfighters.game.core.fubar.TileType;
import de.bergwerklabs.dungeonfighters.game.core.fubar.Util;
import de.bergwerklabs.dungeonfighters.game.core.map.Dungeon;
import de.bergwerklabs.dungeonfighters.game.core.map.DungeonLoader;
import de.bergwerklabs.dungeonfighters.util.DestructionWarning;
import de.bergwerklabs.framework.core.inventorymenu.InventoryMenuFactory;
import de.bergwerklabs.framework.core.scoreboard.LabsScoreboard;
import de.bergwerklabs.framework.core.scoreboard.LabsScoreboardFactory;
import de.bergwerklabs.util.GameStateManager;
import de.bergwerklabs.util.LABSGameMode;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.*;

/**
 * Created by Yannic Rieger on 25.04.2017.
 * <p> Main class for the DungeonFighter minigame. </p>
 * @author Yannic Rieger
 */
public class Main extends LABSGameMode
{
    /**
     * Returns the current instance of the Main class.
     */
    public static Main getInstance() { return instance; }

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

    public final static DungeonFighters game = new DungeonFighters();
    private static Main instance;

    private File configFile = new File(this.getDataFolder() + "/config.json");
    private File menuFolder = new File(this.getDataFolder() + "/menus");
    private File shopFolder = new File(this.getDataFolder() + "/shops");

    private LabsScoreboard scoreboard;
    private DungeonFighterConfig config;
    private DungeonLoader loader;

    private List<BukkitTask> tasks = new ArrayList<>();


    // TODO: put tasks in list an cancle them if needed.

    @Override
    public void labsEnable() {
        instance = this;
        this.loader = new DungeonLoader();


        this.getServer().getPluginManager().registerEvents(new DungeonFightersEventHandler(), this);

        try {
            this.config = new GsonBuilder().registerTypeAdapter(DungeonFighterConfig.class, new DungeonFighterConfigDeserializer()).create()
                                           .fromJson(new InputStreamReader(new FileInputStream(configFile), Charset.forName("UTF-8")), DungeonFighterConfig.class);

            InventoryMenuFactory.readMenus(menuFolder, null);

            //ShopFactory.readNPCShops(shopFolder);
            //NPCShopManager.getShops().values().forEach(shop -> shop.spawnShop());

            scoreboard = LabsScoreboardFactory.createInstance(this.getDataFolder() + "/scoreboard.json");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        this.loader.generate(this.config.getGridOrigin(), this.determineDungeon());
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

            List<Chunk> chunks = Arrays.asList(loader.getChunks().clone());
            Collections.shuffle(chunks);
            Iterator<Chunk> it = chunks.iterator();

            Bukkit.getOnlinePlayers().forEach(player -> player.teleport(it.next().getBlock(9, 9, 9).getLocation()));

            Bukkit.getScheduler().runTaskLater(this, () -> {
                this.loader.startDestructionSequence();
                Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
                    Bukkit.getOnlinePlayers().forEach(player ->  {
                        if (this.loader.getDestructedChunks().contains(player.getLocation().getChunk())) {
                            DestructionWarning.sendPacket(player, true);
                        }
                        else DestructionWarning.sendPacket(player, false);
                    });
                }, 5L, 5L);
            }, 20 * 6L); // TODO: start when challenge finished
            return true;
        }
        return false;
    }

    @Override
    public String getChatPrefix() {
        return "§6>> §eDungeonFighters §6❘ §7";
    }

    /**
     * Randomly determines the map that will be played
     * @return Folder containing the module schematics.
     */
    private Dungeon determineDungeon() {
        File[] maps = new File(this.getDataFolder() + "/maps").listFiles();

        TileType[] grid = Generator.generateMap(10);
        this.generateAndSaveImage(grid, 10);
        SecureRandom random = new SecureRandom();

        return new Dungeon(maps[random.nextInt(maps.length)], grid);
    }

    /**
     *
     * @param grid
     * @param mapSize
     */
    private void generateAndSaveImage(TileType[] grid, int mapSize) {
        try {
            ImageIO.write(Util.createImageFromBoard(grid, mapSize), "png", new File("/home/freggy/laby/spigot_1.8.8/plugins/DungeonFighters/board.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
