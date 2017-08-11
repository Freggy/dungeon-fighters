package de.bergwerklabs.dungeonfighters.game.core.games;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.commons.Util;
import de.bergwerklabs.dungeonfighters.game.core.DungeonSession;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation.ActivationInfo;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation.ActivationLine;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.generation.DungeonModuleConstructor;

import java.util.List;

/**
 * Created by Yannic Rieger on 19.07.2017.
 * <p>
 * This task updates the game and checks whether or not the player is in another module.
 * If so, the task will start the game.
 *
 * @author Yannic Rieger
 */
public class GameUpdateTask implements Runnable {

    private List<ActivationLine> lines;

    /**
     *
     */
    public GameUpdateTask() {
        this.lines = DungeonFightersPlugin.game.getPath().getLines();
    }

    @Override
    public void run() {
        DungeonFightersPlugin.game.getPlayerManager().getPlayers().values().forEach(fighter -> {
            this.lines.forEach(line -> {
                DungeonSession session = fighter.getSession();

                if (line.shouldActivate(fighter.getPlayer().getLocation())) {
                    ActivationInfo info = line.getInfo();
                    ActivationLine nextLine = line.getNextLine();

                    if (!nextLine.isModuleBuilt()) nextLine.buildAssociatedGame();
                    this.lines.remove(line);
                    Util.closeEntrance(fighter.getPlayer(), info.getProviderResult().getBuildLocation(), DungeonModuleConstructor.getBarrierWalls());

                    session.getCurrentGame().stop();
                    session.setCurrentGame(info.getProvider());
                    session.getCurrentGame().assignPlayer(fighter);
                    session.getCurrentGame().assignModule(info.getProviderResult().getModule());
                    session.getCurrentGame().start();
                }
            });
        });
    }
}
