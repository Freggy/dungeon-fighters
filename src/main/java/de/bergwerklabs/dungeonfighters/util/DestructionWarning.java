package de.bergwerklabs.dungeonfighters.util;

import de.bergwerklabs.framework.commons.spigot.general.reflection.LabsReflection;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.WorldBorder;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 21.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class DestructionWarning {

    private static PacketPlayOutWorldBorder displayWorldBorderPacket;
    private static PacketPlayOutWorldBorder removeWorldBorderPacket;

    public static void sendPacket(Player player, boolean display) {
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        WorldBorder playerWorldBorder = nmsPlayer.world.getWorldBorder();

        if (display) {
            if (displayWorldBorderPacket == null) {
                displayWorldBorderPacket = createDisplayPacket(playerWorldBorder, 1000000000);
            }
            nmsPlayer.playerConnection.sendPacket(displayWorldBorderPacket);
        }
        else {
            if (removeWorldBorderPacket == null) {
                removeWorldBorderPacket = createDisplayPacket(playerWorldBorder, 0);
            }
            nmsPlayer.playerConnection.sendPacket(removeWorldBorderPacket);
        }
    }

    private static PacketPlayOutWorldBorder createDisplayPacket(WorldBorder border, int warningDistance) {
        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(border, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS);
        LabsReflection.setFieldValue(LabsReflection.getField(packetPlayOutWorldBorder.getClass(), "i"), packetPlayOutWorldBorder, warningDistance);
        return packetPlayOutWorldBorder;
    }
}
