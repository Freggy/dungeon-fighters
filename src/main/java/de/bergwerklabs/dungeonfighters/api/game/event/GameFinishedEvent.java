package de.bergwerklabs.dungeonfighters.api.game.event;

import de.bergwerklabs.dungeonfighters.api.game.DungeonGame;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;

/**
 * Created by Yannic Rieger on 18.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class GameFinishedEvent extends AbstractGameEvent {
    /**
     * @param fighter
     * @param game
     */
    public GameFinishedEvent(DungeonFighter fighter, DungeonGame game) {
        super(fighter, game);
    }
}
