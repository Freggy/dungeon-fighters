package de.bergwerklabs.dungeonfighters.game.core.games;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.dungeonfighters.api.StageTier;
import de.bergwerklabs.dungeonfighters.api.game.DungeonGame;
import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.api.game.event.GameFinishedEvent;
import de.bergwerklabs.dungeonfighters.commons.Util;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.game.core.DungeonSession;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation.ActivationInfo;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation.ActivationLine;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.generation.DungeonModuleConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

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

    public GameUpdateTask() {
        this.lines = DungeonFightersPlugin.game.getPath().getLines();
    }

    @Override
    public void run() {
        for (DungeonFighter fighter : DungeonFightersPlugin.game.getPlayerManager().getPlayers().values()) {
            for (ActivationLine line : this.lines) {
                DungeonSession session = fighter.getSession();
                Player player = fighter.getPlayer();

                if (line != null) {
                    if (line.getInfo().hasWall()) line.tryVanishWall(player);

                    if (line.shouldActivate(player.getLocation())) {
                        StageTier tier = line.getInfo().getProvider().getTier();

                        if (this.checkPreconditions(session, tier)) continue;

                        ActivationLine nextLine = line.getNextLine();
                        ActivationInfo info = line.getInfo();

                        // nextLine is null when the last module is activated.
                        if (nextLine != null && !nextLine.isModuleBuilt()) nextLine.buildAssociatedGame(true);

                        this.lines.remove(line);
                        Util.closeEntrance(player, info.getProviderResult().getBuildLocation(), DungeonModuleConstructor.getBarrierWalls());
                        player.getInventory().clear();
                        DungeonMechanicProvider provider = info.getProvider();
                        PluginManager manager = Bukkit.getPluginManager();
                        session.getCurrentGame().stop();

                        if (provider instanceof DungeonGame) {
                            DungeonGame game = (DungeonGame)provider;
                            manager.enablePlugin(game);
                            manager.registerEvents(game, game);
                        }

                        if (session.getCurrentGame() instanceof DungeonGame)
                            manager.callEvent(new GameFinishedEvent(fighter, (DungeonGame) session.getCurrentGame()));

                        session.setCurrentGame(provider);
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
               (session.getCurrentGame().getId().equals("battleZone-built-in") && gameTier.isLowerTierThan(session.getCurrentTier()));
    }
}
