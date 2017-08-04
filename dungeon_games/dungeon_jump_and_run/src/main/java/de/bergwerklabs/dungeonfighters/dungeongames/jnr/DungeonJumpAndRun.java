package de.bergwerklabs.dungeonfighters.dungeongames.jnr;

import com.google.gson.GsonBuilder;
import de.bergwerklabs.dungeonfighters.api.game.DungeonGame;
import de.bergwerklabs.dungeonfighters.api.game.config.BaseConfigDeserializer;
import de.bergwerklabs.dungeonfighters.dungeongames.jnr.config.JnrConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by Yannic Rieger on 26.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class DungeonJumpAndRun extends DungeonGame {

    private JnrConfig config;
    private Location spawn;

    @Override
    public String getId() {
        return "dungeon-jump-and-run";
    }

    @Override
    public void start() {
        System.out.println("start");
        this.hasStarted = true;
    }

    @Override
    public void stop() {
        System.out.println("stop");
        this.hasStarted = false;
    }

    @Override
    public void reset() {
        System.out.println("reset");
    }

    @Override
    public void onLoad() {
        //this.loadConfig();
        //NbtUtil.vectorFromNbt(NbtUtil.readCompoundTag(this.module.getSchematicFile()));
    }

    /**
     * Loads the config for this game.
     */
    private void loadConfig() {
        try {
            this.config = new GsonBuilder().registerTypeAdapter(JnrConfig.class, new BaseConfigDeserializer()).create()
                                           .fromJson(new InputStreamReader(new FileInputStream(configLocation), Charset.forName("UTF-8")), JnrConfig.class);
            System.out.println("Loaded config for " + this.getId());
        }
        catch (FileNotFoundException e) {
            Bukkit.getLogger().info("Jnr config file could not be found.");
        }
    }
}
