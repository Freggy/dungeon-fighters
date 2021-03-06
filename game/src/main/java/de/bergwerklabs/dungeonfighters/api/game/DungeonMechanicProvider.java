package de.bergwerklabs.dungeonfighters.api.game;

import de.bergwerklabs.dungeonfighters.api.StageTier;
import de.bergwerklabs.dungeonfighters.api.module.ModuleMetadata;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.dungeonfighters.game.core.games.map.path.activation.ActivationLine;
import de.bergwerklabs.framework.schematicservice.LabsSchematic;

/**
 * Created by Yannic Rieger on 11.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public interface DungeonMechanicProvider extends Cloneable {

    /**
     *
     */
    ActivationLine getNextLine();

    /**
     *
     */
    DungeonFighter getFighter();

    /**
     *
     */
    String getId();

    /**
     *
     * @return
     */
    StageTier getTier();

    /**
     *
     * @param info
     */
    void assignNext(ActivationLine info);

    /**
     *
     * @param fighter
     */
    void assignPlayer(DungeonFighter fighter);

    /**
     *
     * @param schematic
     */
    void assignModule(LabsSchematic schematic);

    /**
     *
     * @return
     */
    <T extends ModuleMetadata> LabsSchematic<T> getModule();

    /**
     *
     */
    void start();

    /**
     *
     */
    boolean hasStarted();

    /**
     *
     * @return
     */
    Object clone();

    /**
     *
     */
    void stop();

    /**
     *
     */
    void reset();
}
