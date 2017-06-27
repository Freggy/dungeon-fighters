package de.bergwerklabs.dungeonfighters.game.core.games.map;

import java.io.File;
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

    public List<DungeonGameModule> getModules() {
        return modules;
    }

    private File game;
    private List<DungeonGameModule> modules;

    public DungeonGameWrapper(File game, List<DungeonGameModule> modules) {
        this.game = game;
        this.modules = modules;
    }
}