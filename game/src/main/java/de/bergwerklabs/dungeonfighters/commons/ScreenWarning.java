package de.bergwerklabs.dungeonfighters.commons;

import de.bergwerklabs.framework.commons.spigot.nms.packet.v1_8.WrapperPlayServerWorldBorder;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 21.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class ScreenWarning {

    /**
     * 
     * @param player
     * @param display
     */
    public static void send(Player player, Boolean display) {
        WrapperPlayServerWorldBorder worldBorderPacket = new WrapperPlayServerWorldBorder();
        worldBorderPacket.setWarningDistance(display ? 0 : 1000000000);
        worldBorderPacket.sendPacket(player);
    }
}
