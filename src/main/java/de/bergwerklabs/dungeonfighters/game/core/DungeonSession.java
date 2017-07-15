package de.bergwerklabs.dungeonfighters.game.core;

import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;

import java.util.HashSet;

/**
 * Created by Yannic Rieger on 04.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class DungeonSession {

    private class DummyGame implements DungeonMechanicProvider {

        @Override
        public HashSet<String> getChunks() {
            return null;
        }

        @Override
        public DungeonFighter getFighter() {
            return null;
        }

        @Override
        public String getId() {
            return "stub";
        }

        @Override
        public void assignChunks(HashSet<String> chunks) {}

        @Override
        public void assignPlayer(DungeonFighter fighter) {}

        @Override
        public void start() {}

        @Override
        public void stop() {}
    }

    /**
     *
     */
    public DungeonMechanicProvider getCurrentGame() { return this.currentGame; }

    /**
     *
     * @param provider
     */
    public void setCurrentGame(DungeonMechanicProvider provider) { this.currentGame = provider; }

    private DungeonMechanicProvider currentGame = new DummyGame();
}
