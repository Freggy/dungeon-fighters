package de.bergwerklabs.dungeonfighters.game.core.games.mechanic;

/**
 * Created by Yannic Rieger on 01.08.2017.
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
        this.hasStarted = true;
    }
}
