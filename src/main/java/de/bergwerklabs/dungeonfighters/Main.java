package de.bergwerklabs.dungeonfighters;

import com.google.gson.GsonBuilder;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFightersEventHandler;
import de.bergwerklabs.dungeonfighters.game.config.ConfigDeserializer;
import de.bergwerklabs.dungeonfighters.game.config.DungeonFighterConfig;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighters;
import de.bergwerklabs.dungeonfighters.game.map.Dungeon;
import de.bergwerklabs.dungeonfighters.game.map.DungeonLoader;
import de.bergwerklabs.framework.inventorymenu.InventoryMenuFactory;
import de.bergwerklabs.framework.scoreboard.LabsScoreboard;
import de.bergwerklabs.framework.scoreboard.LabsScoreboardFactory;
import de.bergwerklabs.framework.shop.NPCShopManager;
import de.bergwerklabs.framework.shop.ShopFactory;
import de.bergwerklabs.util.GameStateManager;
import de.bergwerklabs.util.LABSGameMode;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.SecureRandom;

/**
 * Created by Yannic Rieger on 25.04.2017.
 * <p> Main class for the DungeonFighter minigame. </p>
 *
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

    public final static DungeonFighters game = new DungeonFighters();
    private static Main instance;

    private File configFile = new File(this.getDataFolder() + "/config.json");
    private File menuFolder = new File(this.getDataFolder() + "/menus");
    private File shopFolder = new File(this.getDataFolder() + "/shops");

    private LabsScoreboard scoreboard;
    private DungeonFighterConfig config;

    @Override
    public void labsEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents(new DungeonFightersEventHandler(), this);



        try {
            this.config = new GsonBuilder().registerTypeAdapter(DungeonFighterConfig.class, new ConfigDeserializer()).create()
                                           .fromJson(new InputStreamReader(new FileInputStream(configFile), Charset.forName("UTF-8")), DungeonFighterConfig.class);

            InventoryMenuFactory.readMenus(menuFolder, null);

            ShopFactory.readNPCShops(shopFolder);
            NPCShopManager.getShops().values().forEach(shop -> shop.spawnShop());

            scoreboard = LabsScoreboardFactory.createInstance(this.getDataFolder() + "/scoreboard.json");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        DungeonLoader loader = new DungeonLoader(this.config.getGridOrigin());

        loader.prepareMap(this.determineDungeon());
        // TODO: place modules based on map
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
        if (commandLabel.equalsIgnoreCase("money")) {

            return true;
        }
        return false;
    }

    /**
     * Randomly determines the map that will be played
     * @return Folder containing the module schematics.
     */
    private Dungeon determineDungeon() {
        File[] maps = new File(this.getDataFolder() + "/maps").listFiles();
        return new Dungeon(maps[new SecureRandom().nextInt(maps.length)]);
    }
}
