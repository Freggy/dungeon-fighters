package de.bergwerklabs.dungeonfighters.api.game.event;

import de.bergwerklabs.dungeonfighters.api.game.DungeonGame;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.framework.commons.spigot.general.LabsEvent;

/**
 * Created by Yannic Rieger on 15.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
abstract class AbstractGameEvent extends LabsEvent {
    /**
     * Gets the game that the player did not finish successfully.
     */
    public DungeonGame getGame() {
        return game;
    }

    /**
     * Gets the {@link DungeonFighter} that did not finish successfully.
     */
    public DungeonFighter getFighter() {
        return fighter;
    }

    protected DungeonFighter fighter;
    protected DungeonGame game;

    /**
     * @param fighter
     * @param game
     */
    public AbstractGameEvent(DungeonFighter fighter, DungeonGame game) {
        this.fighter = fighter;
        this.game = game;
    }

}
