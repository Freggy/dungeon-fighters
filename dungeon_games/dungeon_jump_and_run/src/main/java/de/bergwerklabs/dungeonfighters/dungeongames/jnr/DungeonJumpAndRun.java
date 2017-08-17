package de.bergwerklabs.dungeonfighters.dungeongames.jnr;

import com.google.gson.GsonBuilder;
import de.bergwerklabs.dungeonfighters.api.game.DungeonGame;
import de.bergwerklabs.dungeonfighters.api.game.config.BaseConfigDeserializer;
import de.bergwerklabs.dungeonfighters.api.game.config.BaseDungeonGameConfig;
import de.bergwerklabs.dungeonfighters.api.game.event.GameFailEvent;
import de.bergwerklabs.dungeonfighters.commons.ScreenWarning;
import de.bergwerklabs.dungeonfighters.commons.Util;
import de.bergwerklabs.framework.commons.spigot.general.timer.LabsTimer;
import de.bergwerklabs.framework.commons.spigot.item.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by Yannic Rieger on 26.07.2017.
 * <p>
 *
 * @author Yannic Rieger
 */
public class DungeonJumpAndRun extends DungeonGame {

    private BaseDungeonGameConfig config;
    private LabsTimer timer;
    private int tries = 0;
    private boolean cooldown = false;

    @Override
    public String getId() {
        return "dungeon-jump-and-run";
    }

    @Override
    public void start() {
        Player player = fighter.getPlayer();
        player.getInventory().setItem(4, new ItemStackBuilder(Material.INK_SACK)
              .setData((byte)1).setName("§c§lInstant-Tod(TM) §7§o<Rechtsklick>").create());

        this.timer = new LabsTimer(this.config.getDuration(), (timeLeft) -> {
            if (timeLeft == this.config.getWarningTime())
                ScreenWarning.send(player, true);
            Util.sendTimerHoverText(player, this.config.getTimerString(), timeLeft);
        });

        timer.start();
        this.hasStarted = true;
    }

    @Override
    public void stop() {
        // has to be done before the timer stops because the LabsTimer#timeLeft() could be reseted to a default value
        // and could therefore lead to a false gold calculation.
        this.fighter.getSession().addGold(this.tier.calculateGold((double)this.timer.getDuration(), (double)this.timer.timeLeft()));
        this.timer.stop();
        this.hasStarted = false;
        HandlerList.unregisterAll((Listener) this);
    }

    @Override
    public void reset() {
        System.out.println("reset");
    }

    @Override
    public void onLoad() {
        this.loadConfig();
        //NbtUtil.vectorFromNbt(NbtUtil.readCompoundTag(this.module.getSchematicFile()));
    }

    /**
     * Loads the config for this game.
     */
    private void loadConfig() {
        try {
            this.config = new GsonBuilder().registerTypeAdapter(BaseDungeonGameConfig.class, new BaseConfigDeserializer()).create()
                                           .fromJson(new InputStreamReader(new FileInputStream(configLocation), Charset.forName("UTF-8")), BaseDungeonGameConfig.class);
            Bukkit.getLogger().info("Loaded config for " + this.getId());
        }
        catch (FileNotFoundException e) {
            Bukkit.getLogger().info("Config file " + this.getId() + " could not be found.");
        }
    }

    @EventHandler
    private void onDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (player.getUniqueId().equals(this.fighter.getPlayer().getUniqueId())) {
            System.out.println("1");
            Action action = e.getAction();
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                if (!cooldown) {
                    ItemStack item = player.getItemInHand();
                    if (item != null && item.getType() == Material.INK_SACK) {
                        cooldown = true;
                        System.out.println("2");
                        Bukkit.getPluginManager().callEvent(new GameFailEvent(this.fighter, this, tries++));
                        Bukkit.getScheduler().runTaskLaterAsynchronously(this, () -> cooldown = false, 25L);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
