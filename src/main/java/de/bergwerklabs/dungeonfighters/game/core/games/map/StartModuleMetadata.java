package de.bergwerklabs.dungeonfighters.game.core.games.map;

import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import org.bukkit.util.Vector;

/**
 * Created by Yannic Rieger on 29.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
class StartModuleMetadata extends ModuleMetadata {

    Vector getSpawn() {
        return spawn;
    }

    private Vector spawn;

    StartModuleMetadata(Vector end, Vector spawn) {
        super(end);
        this.spawn = spawn;
    }
}
