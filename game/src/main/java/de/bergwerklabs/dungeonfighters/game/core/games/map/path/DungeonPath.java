package de.bergwerklabs.dungeonfighters.game.core.games.map.path;

import de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation.ActivationLine;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DungeonPath {

    public List<Location> getSpawns() {
        return spawns;
    }

    public List<ActivationLine> getLines() {
        return lines;
    }

    private List<ActivationLine> lines = new CopyOnWriteArrayList<>();
    private List<Location> spawns = new ArrayList<>();
}
