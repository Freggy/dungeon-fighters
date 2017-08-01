package de.bergwerklabs.dungeonfighters.game.core.games.map.path;

import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;

import java.util.Queue;

/**
 * Created by Yannic Rieger on 01.08.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class DungeonPath {

    /**
     *
     * @return
     */
    public Queue<DungeonMechanicProvider> getGames() { return this.games; }

    private Queue<DungeonMechanicProvider> games;
}
