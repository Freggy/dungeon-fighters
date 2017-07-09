package de.bergwerklabs.dungeonfighters.game.core.games.map.metadata;

import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import org.bukkit.util.Vector;

/**
 * Created by Yannic Rieger on 29.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class StartModuleMetadata extends ModuleMetadata {

    Vector getSpawn() {
        return spawn;
    }

    private Vector spawn;

    public StartModuleMetadata(Vector end, short length, Vector spawn) {
        super(end, length);
        this.spawn = spawn;
    }
}
