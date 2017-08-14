package de.bergwerklabs.dungeonfighters.dungeongames.jnr;

import com.google.gson.GsonBuilder;
import de.bergwerklabs.dungeonfighters.api.game.DungeonGame;
import de.bergwerklabs.dungeonfighters.api.game.config.BaseConfigDeserializer;
import de.bergwerklabs.dungeonfighters.api.game.config.BaseDungeonGameConfig;
import de.bergwerklabs.dungeonfighters.commons.ScreenWarning;
import de.bergwerklabs.dungeonfighters.commons.Util;
import de.bergwerklabs.framework.commons.spigot.general.timer.LabsTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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

    @Override
    public String getId() {
        return "dungeon-jump-and-run";
    }

    @Override
    public void start() {
        Player player = fighter.getPlayer();
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
            Bukkit.getLogger().info("Config file could not be found.");
        }
    }
}
