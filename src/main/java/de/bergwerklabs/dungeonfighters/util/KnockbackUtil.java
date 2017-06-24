package de.bergwerklabs.dungeonfighters.util;

/**
 * Created by Yannic Rieger on 24.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class KnockbackUtil {

    public static final String name = "§eKnockback-Stick §6❘ §eAufladung:§b {percentage}%";


    public static int getPower(String s) {
       return Integer.valueOf(s.split(":")[1].replace("%", "")
                                                .replace("§b", "").trim());
    }

}
