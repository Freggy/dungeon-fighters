package de.bergwerklabs.dungeonfighters.game.core.games;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.StageTier;
import de.bergwerklabs.dungeonfighters.commons.ScreenWarning;
import de.bergwerklabs.dungeonfighters.commons.Util;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.game.core.DungeonSession;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation.ActivationInfo;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation.ActivationLine;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.generation.DungeonModuleConstructor;
import org.bukkit.Location;

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
        for (DungeonFighter fighter : DungeonFightersPlugin.game.getPlayerManager().getPlayers().values()) {
            for (ActivationLine line : this.lines) {
                DungeonSession session = fighter.getSession();
                Location playerLocation = fighter.getPlayer().getLocation();
                if (line != null) {
                    if (line.getInfo().hasWall()) line.tryVanishWall(playerLocation);

                    if (line.shouldActivate(playerLocation)) {
                        StageTier tier = line.getInfo().getProvider().getTier();
                        if (this.checkPreconditions(session, tier)) continue;

                        ActivationLine nextLine = line.getNextLine();
                        ActivationInfo info = line.getInfo();

                        if (!nextLine.isModuleBuilt()) nextLine.buildAssociatedGame(true);

                        this.lines.remove(line);
                        Util.closeEntrance(fighter.getPlayer(), info.getProviderResult().getBuildLocation(), DungeonModuleConstructor.getBarrierWalls());
                        fighter.getPlayer().getInventory().clear();

                        ScreenWarning.send(fighter.getPlayer(), false); // remove red screen border from last minigame, if there is one.
                        session.getCurrentGame().stop();
                        session.setCurrentGame(info.getProvider());
                        session.getCurrentGame().assignPlayer(fighter);
                        session.getCurrentGame().assignModule(info.getProviderResult().getModule());
                        session.getCurrentGame().start();
                    }
                }
            }
        }
    }

    // Prevent players that are already in a battle zone from activating others lines.
    private boolean checkPreconditions(DungeonSession session, StageTier gameTier) {
        return (session.getCurrentGame().getId().equals("battleZone-built-in") && gameTier == null) ||
               (session.getCurrentGame().getId().equals("battleZone-built-in") && gameTier.isLowerTier(session.getCurrentTier()));
    }
}
