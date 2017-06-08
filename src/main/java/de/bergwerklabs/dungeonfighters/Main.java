package de.bergwerklabs.dungeonfighters;

import com.google.gson.GsonBuilder;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFightersEventHandler;
import de.bergwerklabs.dungeonfighters.game.config.ConfigDeserializer;
import de.bergwerklabs.dungeonfighters.game.config.DungeonFighterConfig;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighters;
import de.bergwerklabs.dungeonfighters.game.map.Dungeon;
import de.bergwerklabs.fmga.algorithm.FMGA;
import de.bergwerklabs.fmga.algorithm.FmgaFactory;
import de.bergwerklabs.fmga.algorithm.cycle.GenerationCycleFixPoint;
import de.bergwerklabs.fmga.algorithm.grid.GridCoordinate;
import de.bergwerklabs.fmga.algorithm.module.Direction;
import de.bergwerklabs.fmga.algorithm.util.PointCompound;
import de.bergwerklabs.framework.core.inventorymenu.InventoryMenuFactory;
import de.bergwerklabs.framework.core.scoreboard.LabsScoreboard;
import de.bergwerklabs.framework.core.scoreboard.LabsScoreboardFactory;
import de.bergwerklabs.framework.core.shop.NPCShopManager;
import de.bergwerklabs.framework.core.shop.ShopFactory;
import de.bergwerklabs.util.GameStateManager;
import de.bergwerklabs.util.LABSGameMode;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

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

    public final static DungeonFighters game = new DungeonFighters();
    private static Main instance;

    private File configFile = new File(this.getDataFolder() + "/config.json");
    private File menuFolder = new File(this.getDataFolder() + "/menus");
    private File shopFolder = new File(this.getDataFolder() + "/shops");

    public static final SecureRandom random = new SecureRandom();
    private LabsScoreboard scoreboard;
    private DungeonFighterConfig config;
    private FMGA fmga;

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

            this.fmga = FmgaFactory.createFmga(this.getDataFolder() + "/fmga.json");

            scoreboard = LabsScoreboardFactory.createInstance(this.getDataFolder() + "/scoreboard.json");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

         //new DungeonLoader(this.config.getGridOrigin()).prepareMap(this.determineDungeon());
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
        return new Dungeon(maps[random.nextInt(maps.length)], this.fmga.generate(this.getCompunds()));
    }

    private List<PointCompound> getCompunds() {
        ArrayList<PointCompound> compounds = new ArrayList<>();
        compounds.add(new PointCompound(new GenerationCycleFixPoint(new GridCoordinate(1, this.random.nextInt(8) + 1), Direction.SOUTH), new GenerationCycleFixPoint(new GridCoordinate(3, 4), Direction.NORTH)));
        compounds.add(new PointCompound(new GenerationCycleFixPoint(new GridCoordinate(this.random.nextInt(8) + 1, 8), Direction.WEST), new GenerationCycleFixPoint(new GridCoordinate(4, 6), Direction.EAST)));
        compounds.add(new PointCompound(new GenerationCycleFixPoint(new GridCoordinate(8, this.random.nextInt(8) + 1), Direction.NORTH), new GenerationCycleFixPoint(new GridCoordinate(6, 5), Direction.SOUTH)));
        compounds.add(new PointCompound(new GenerationCycleFixPoint(new GridCoordinate(this.random.nextInt(8) + 1, 1), Direction.EAST), new GenerationCycleFixPoint(new GridCoordinate(5, 3), Direction.WEST)));
        return compounds;
    }
}
