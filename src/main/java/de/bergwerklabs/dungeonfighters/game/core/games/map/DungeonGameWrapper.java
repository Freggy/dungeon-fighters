package de.bergwerklabs.dungeonfighters.game.core.games.map;

import de.bergwerklabs.framework.schematicservice.LabsSchematic;

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

    public File getGame() {
        return game;
    }

    public LabsSchematic getModule() {
        return module;
    }

    private File game;
    private LabsSchematic module;

    public DungeonGameWrapper(File game, List<LabsSchematic> modules) {
        this.game = game;
        this.module = modules.get(new SecureRandom().nextInt(modules.size()));
    }
}
