package de.bergwerklabs.dungeonfighters.api.game.event;

import de.bergwerklabs.dungeonfighters.api.game.DungeonGame;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;

public class GameFailEvent extends AbstractGameEvent {

    /**
     *
     */
    public int getTries() {
        return tries;
    }

    private int tries;

    /**
     * @param fighter
     * @param game
     */
    public GameFailEvent(DungeonFighter fighter, DungeonGame game, int tries) {
        super(fighter, game);
        this.tries = tries;
    }
}
