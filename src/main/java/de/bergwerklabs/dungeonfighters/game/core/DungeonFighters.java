package de.bergwerklabs.dungeonfighters.game.core;

import de.bergwerklabs.framework.game.PlayerManager;

/**
 * Created by Yannic Rieger on 01.05.2017.
 * <p>  </p>
 * @author Yannic Rieger
 */
public class DungeonFighters {

    public PlayerManager<DungeonFighter> getPlayerManager() {
        return playerManager;
    }

    private PlayerManager<DungeonFighter> playerManager = new PlayerManager<>();

    private static DungeonFighters instance;

    public DungeonFighters() {
        if (instance != null) return;
        instance = this;
    }
}
