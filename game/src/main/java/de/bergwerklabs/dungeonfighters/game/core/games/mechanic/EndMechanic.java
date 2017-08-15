package de.bergwerklabs.dungeonfighters.game.core.games.mechanic;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import org.bukkit.Bukkit;

/**
 * Created by Yannic Rieger on 11.07.2017.
 * <p>
 * Mechanic that will be triggered when a player enters the end module.
 *
 * @author Yannic Rieger
 */
public class EndMechanic extends BaseMechanic {

    @Override
    public String getId() {
        return  "end-built-in";
    }

    @Override
    public void start() {
        Bukkit.broadcastMessage(DungeonFightersPlugin.getInstance().getChatPrefix() + "ENDE :)");
        this.hasStarted = true;
    }
}
