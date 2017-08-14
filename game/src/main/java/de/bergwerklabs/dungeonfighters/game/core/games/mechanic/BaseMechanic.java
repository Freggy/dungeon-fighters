package de.bergwerklabs.dungeonfighters.game.core.games.mechanic;

import de.bergwerklabs.dungeonfighters.api.StageTier;
import de.bergwerklabs.dungeonfighters.api.game.DungeonMechanicProvider;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation.ActivationLine;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;

/**
 * Created by Yannic Rieger on 11.07.2017.
 * <p> Base {@link DungeonMechanicProvider} for the built-in functionality.
 *
 * @author Yannic Rieger
 */
public abstract class BaseMechanic implements DungeonMechanicProvider {

    @Override
    public ActivationLine getNextLine() {
        return this.line;
    }

    @Override
    public DungeonFighter getFighter() {
        return this.fighter;
    }

    @Override
    public boolean hasStarted() {
        return this.hasStarted;
    }

    protected ActivationLine line;
    protected DungeonFighter fighter;
    protected LabsSchematic schematic;
    protected boolean hasStarted;

    @Override
    public void assignNext(ActivationLine line) {
        this.line = line;
    }

    @Override
    public void assignPlayer(DungeonFighter fighter) {
        this.fighter = fighter;
    }

    @Override
    public void stop() { this.hasStarted = false; }

    @Override
    public void reset() {}

    @Override
    public StageTier getTier() {
        return null;
    }

    @Override
    public void assignModule(LabsSchematic schematic) {
        this.schematic = schematic;
    }

    @Override
    public LabsSchematic getModule() {
        return this.schematic;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
