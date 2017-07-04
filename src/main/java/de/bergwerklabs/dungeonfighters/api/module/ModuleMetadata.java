package de.bergwerklabs.dungeonfighters.api.module;

import org.bukkit.util.Vector;

/**
 * Created by Yannic Rieger on 29.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class ModuleMetadata {

    /**
     * Gets the end point of this module.
     */
    public Vector getEnd() {
        return end.clone().add(new Vector(0, 0, 1));
    }

    /**
     * Gets the length of this module in chunks.
     */
    public short getLength() {
        return length;
    }

    private Vector end;
    private short length;

    /**
     * @param end
     * @param length
     */
    public ModuleMetadata(Vector end, short length) {
        this.end = end;
        this.length = length;
    }
}
