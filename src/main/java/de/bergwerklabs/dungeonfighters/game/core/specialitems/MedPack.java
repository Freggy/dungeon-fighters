package de.bergwerklabs.dungeonfighters.game.core.specialitems;

import de.bergwerklabs.dungeonfighters.Main;
import de.bergwerklabs.dungeonfighters.api.SpecialItem;
import de.bergwerklabs.dungeonfighters.util.ParticleUtil;
import de.bergwerklabs.util.effect.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Yannic Rieger on 22.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class MedPack implements SpecialItem {

    @Override
    public void use(Player player) {
        double health = player.getHealth();
        double healing = Main.getInstance().getDungeonFighterConfig().getMedPackHealing();

        // if set a number higher than 20 a exception is thrown
        if (health == 20) {
            player.sendMessage(Main.getInstance().getChatPrefix() +
                                  Main.getInstance().getDungeonFighterConfig().getCannotUseMedPackMessage());
            return;
        }

        // Check if our initial healing would cause an exception
        if (health + 4 > 20) healing = 20 - health; // gets the remaining healing needed to fully heal the player.

        player.setHealth(health + healing);
        player.playSound(player.getEyeLocation(), Sound.VILLAGER_YES, 100, 1);

        player.getNearbyEntities(20, 20, 20).forEach(entity -> {
            if (entity instanceof Player) {
                Particle particle = ParticleUtil.createParticle(Particle.ParticleEffect.HEART, player.getEyeLocation().add(0, 0.3, 0), 0.3F, 0.2F, 0.3F, 0F, 4);
                ParticleUtil.sendParticleToPlayer(particle, (Player) entity);
            }
        });

        ItemStack inHand = player.getItemInHand();
        inHand.setAmount(inHand.getAmount() - 1);
    }

    @Override
    public List<Action> getRequiredActions() {
        return Arrays.asList(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK);
    }

    @Override
    public SpecialItemType getType() {
        return SpecialItemType.MED_PACK;
    }
}
