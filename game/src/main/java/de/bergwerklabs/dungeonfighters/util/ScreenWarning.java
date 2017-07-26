package de.bergwerklabs.dungeonfighters.util;

import de.bergwerklabs.framework.commons.spigot.nms.packet.v1_8.WrapperPlayServerWorldBorder;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 21.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class ScreenWarning {

    private static WrapperPlayServerWorldBorder worldBorderPacket = new WrapperPlayServerWorldBorder();

    /**
     *
     * @param player
     * @param display
     */
    public static void send(Player player, boolean display) {
        if (display) {
            worldBorderPacket.setWarningDistance(1000000000);
            worldBorderPacket.sendPacket(player);
        }
        else {
            worldBorderPacket.setWarningDistance(0);
            worldBorderPacket.sendPacket(player);
        }
    }
}
