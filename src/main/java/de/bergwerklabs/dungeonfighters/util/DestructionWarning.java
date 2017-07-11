package de.bergwerklabs.dungeonfighters.util;

import de.bergwerklabs.framework.commons.spigot.nms.packet.v1MV8.WrapperPlayServerWorldBorder;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 21.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class DestructionWarning {

    private static WrapperPlayServerWorldBorder worldBorderPacket = new WrapperPlayServerWorldBorder();

    public static void sendPacket(Player player, boolean display) {
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
