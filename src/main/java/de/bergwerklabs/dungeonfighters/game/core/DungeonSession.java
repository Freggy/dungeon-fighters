package de.bergwerklabs.dungeonfighters.game.core;

import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;

/**
 * Created by Yannic Rieger on 04.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class DungeonSession {

    /**
     *
     */
    public DungeonMechanicProvider getCurrentGame() { return this.currentGame; }

    private DungeonMechanicProvider currentGame;
}
