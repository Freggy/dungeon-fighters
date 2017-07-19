package de.bergwerklabs.dungeonfighters.game.core.games.mechanic;

import de.bergwerklabs.framework.commons.spigot.game.LabsPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 18.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class StartMechanic extends BaseMechanic {

    @Override
    public String getId() {
        return "start-built-in";
    }

    @Override
    public void start() {
        Player player = fighter.getPlayer();
        LabsPlayer.freeze(player);
    }
}
