package de.bergwerklabs.dungeonfighters.game.core;

import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

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
            return new HashSet<>();
        }

        @Override
        public DungeonFighter getFighter() {
            return null;
        }

        @Override
        public String getId() {
            return "bla";
        }

        @Override
        public void assignChunks(HashSet<String> chunks) {

        }

        @Override
        public void assignPlayer(DungeonFighter fighter) {

        }

        @Override
        public void assignModule(LabsSchematic schematic) {

        }

        @Override
        public LabsSchematic getModule() {
            return null;
        }

        @Override
        public void start() {

        }

        @Override
        public Object clone() {
            return null;
        }

        @Override
        public void stop() {

        }

        @Override
        public void reset() {

        }
    }

    /**
     *
     */
    public DungeonMechanicProvider getCurrentGame() { return this.currentGame; }

    /**
     *
     * @param provider
     */
    public void setCurrentGame(DungeonMechanicProvider provider) { this.currentGame = (DungeonMechanicProvider) provider.clone(); }


    public Queue<DungeonMechanicProvider> getGames() { return this.games; }

    /**
     *
     * @param games
     */
    public void setGames(Queue<DungeonMechanicProvider> games) {
        this.games = games;
    }



    private DungeonMechanicProvider currentGame = new DummyGame();
    private Queue<DungeonMechanicProvider> games = new LinkedList<>();
}
