package de.bergwerklabs.dungeonfighters.util;

import org.bukkit.Chunk;

import java.util.Arrays;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class Util {

    public static final String name = "§eKnockback-Stick §6❘ §eAufladung:§b {percentage}%";


    public static int getPower(String s) {
       return Integer.valueOf(s.split(":")[1].replace("%", "")
                                                .replace("§b", "").trim());
    }

    public static String getChunkCoordinateString(Chunk chunk) {
        return Arrays.toString(new Integer[] { chunk.getX(), chunk.getZ() });
    }

}
