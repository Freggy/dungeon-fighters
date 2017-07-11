package de.bergwerklabs.dungeonfighters.game.core.games.map;

import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;
import org.bukkit.Bukkit;

import java.io.File;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by Yannic Rieger on 26.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class DungeonGameWrapper {

    /**
     *
     */
    public DungeonMechanicProvider getGame() {
        return game;
    }

    /**
     *
     */
    public LabsSchematic<ModuleMetadata> getModule() {
        return module;
    }

    private DungeonMechanicProvider game;
    private LabsSchematic<ModuleMetadata> module;

    /**
     *
     * @param game
     * @param modules
     */
    public DungeonGameWrapper(File game, List<File> modules) {
        try {
            this.game = (DungeonMechanicProvider) Bukkit.getServer().getPluginManager().loadPlugin(game);
            this.module = ModuleMetadata.getService().createSchematic(modules.get(new SecureRandom().nextInt(modules.size())));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
