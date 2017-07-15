package de.bergwerklabs.dungeonfighters.api.game.event;

import de.bergwerklabs.dungeonfighters.api.game.DungeonGame;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;

/**
 * Created by Yannic Rieger on 15.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class GameStartEvent extends AbstractGameEvent {

    /**
     * @param fighter
     * @param game
     */
    public GameStartEvent(DungeonFighter fighter, DungeonGame game) {
        super(fighter, game);
    }
}
