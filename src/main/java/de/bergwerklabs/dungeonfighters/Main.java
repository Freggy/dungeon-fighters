package de.bergwerklabs.dungeonfighters;

import de.bergwerklabs.dungeonfighters.game.DungeonFightersEventHandler;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighters;
import de.bergwerklabs.framework.file.FileUtil;
import de.bergwerklabs.framework.inventorymenu.InventoryMenuFactory;
import de.bergwerklabs.framework.scoreboard.LabsScoreboard;
import de.bergwerklabs.framework.scoreboard.LabsScoreboardFactory;
import de.bergwerklabs.framework.shop.NPCShopManager;
import de.bergwerklabs.framework.shop.ShopFactory;
import de.bergwerklabs.util.GameStateManager;
import de.bergwerklabs.util.LABSGameMode;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PotionSplashEvent;

import java.io.File;

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

    private File config = new File(this.getDataFolder() + "/config.json");
    private File menuFolder = new File(this.getDataFolder() + "/menus");
    private File shopFolder = new File(this.getDataFolder() + "/shops");

    private static Main instance;
    public static DungeonFighters game = new DungeonFighters();
    private LabsScoreboard scoreboard;

    @Override
    public void labsEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents(new DungeonFightersEventHandler(), this);
        try {
            this.getLogger().info("Creating folders...");
            FileUtil.createFolderIfNotExistent(this.getDataFolder());
            FileUtil.createFolderIfNotExistent(menuFolder);
            FileUtil.createFolderIfNotExistent(shopFolder);
            FileUtil.createFileIfNotExistent(config);

            InventoryMenuFactory.readMenus(menuFolder, null);

            ShopFactory.readNPCShops(shopFolder);
            NPCShopManager.getShops().values().forEach(shop -> shop.spawnShop());

            scoreboard = LabsScoreboardFactory.createInstance(this.getDataFolder() + "/scoreboard.json");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

    @EventHandler
    private void onPotionSplash(PotionSplashEvent e) {
        e.getPotion().getLocation().getBlock().setType(Material.COBBLESTONE);
    }


}
