package de.bergwerklabs.dungeonfighters.game.controller;

import de.bergwerklabs.dungeonfighters.Main;
import de.bergwerklabs.dungeonfighters.game.core.DungeonFighter;
import de.bergwerklabs.framework.core.general.LabsController;
import de.bergwerklabs.framework.core.inventorymenu.InventoryItem;
import de.bergwerklabs.framework.core.inventorymenu.InventoryItemClickEvent;
import de.bergwerklabs.framework.core.inventorymenu.InventoryMenuManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Yannic Rieger on 29.04.2017.
 * <p>  </p>
 * @author Yannic Rieger
 */
public class BuyController implements LabsController {

    /**
     * Gets invoked when a player buys an item in a shop except the food shop.
     * @param parameters List of parameters containing the following:
     *
     */
    public void onItemBuy(List<Object> parameters) {

        InventoryItemClickEvent event = (InventoryItemClickEvent)parameters.get(0);
        List<String> args = event.getParameter().stream().map(arg -> (String)arg).collect(Collectors.toList());
        InventoryItem item = event.getItem();

        DungeonFighter fighter = Main.game.getPlayerManager().getPlayers().get(event.getEvent().getWhoClicked().getUniqueId());
        Player player = fighter.getPlayer();

        double cost = Double.valueOf(args.get(0));
        boolean isEnchantment = false;

        if(args.size() >= 2) isEnchantment = Boolean.valueOf(args.get(1));

        if (!this.transferEmeralds(fighter, cost)) return;

        if (!isEnchantment) {
            ItemStack boughtItem = item.getItemStack();
            boughtItem.setItemMeta(null); // Clear meta so price and stuff won't be displayed after the item has been bought.
           fighter.getPlayer().getInventory().addItem(boughtItem);
        }
        else {
            try {
                ItemStack itemInHand = player.getItemInHand();
                itemInHand.addEnchantments(item.getItemStack().getEnchantments());
            }
            catch (Exception ex) { // An exception will be thrown if the player tries to enchant an item that cannot be enchanted.
                player.sendMessage(Main.getInstance().getChatPrefix() + Main.getInstance().getDungeonFighterConfig().getCannotEnoughEnchantMessage());
            }
        }
    }

    /**
     * Gets invoked when the player buys food from the food shop.
     * @param parameters List of parameters containing the following:
     */
    public void buyFood(List<Object> parameters) {
        InventoryItemClickEvent event = (InventoryItemClickEvent)parameters.get(0);
        List<String> args = event.getParameter().stream().map(arg -> (String)arg).collect(Collectors.toList());
        InventoryItem item = event.getItem();

        DungeonFighter fighter = Main.game.getPlayerManager().getPlayers().get(event.getEvent().getWhoClicked().getUniqueId());
        int amount = Integer.valueOf(args.get(1));
        double cost = Double.valueOf(args.get(0));

        if (!this.transferEmeralds(fighter, cost)) return;

        ItemStack boughtItem = item.getItemStack();
        boughtItem.setItemMeta(null); // Clear meta so price and stuff won't be displayed after the item has been bought.
        boughtItem.setAmount(amount);

        fighter.getPlayer().getInventory().addItem(boughtItem);
    }

    /**
     * Navigates between menus.
     * @param parameters List of parameters containing the following:
     */
    public void navigateToMenu(List<Object> parameters) {
        InventoryItemClickEvent event = (InventoryItemClickEvent)parameters.get(0);
        event.getEvent().getWhoClicked().openInventory(InventoryMenuManager.getInventoryMenus().get(event.getParameter().get(0)).getInventory());
    }

    /**
     * Converts XP to emeralds. (2 XP = 1 emerald)
     * @param parameters List of parameters containing the following:
     */
    public void convertXp(List<Object> parameters) {
        InventoryItemClickEvent event = (InventoryItemClickEvent)parameters.get(0);

        DungeonFighter fighter = Main.game.getPlayerManager().getPlayers().get(event.getEvent().getWhoClicked().getUniqueId());
        Player player = fighter.getPlayer();

        double money = fighter.getEmeralds();
        int earnedMoney = player.getLevel() / 2;

        if (earnedMoney == 0) {
            player.sendMessage(Main.getInstance().getDungeonFighterConfig().getCannotConvertMessage());
            fighter.getPlayer().playSound(fighter.getPlayer().getEyeLocation(), Sound.NOTE_BASS, 100, 1);
            return;
        }

        money += earnedMoney;

        player.setLevel(player.getLevel() - earnedMoney * 2);
        fighter.earnMoney(money, Sound.ORB_PICKUP);
    }

    /**
     * Transfers emeralds if the player has enough of them to buy the desired item.
     * <p>
     * The cost will be withdrawn if the player has enough emeralds.
     * @param fighter Player who wants to buy an item.
     * @param cost Cost of the item.
     * @return a value indicating whether or not the player is able to buy the item.
     */
    private boolean transferEmeralds(DungeonFighter fighter, double cost) {

        if (!fighter.hasEnoughMoney(cost)) {
            fighter.getPlayer().sendMessage(Main.getInstance().getChatPrefix() + Main.getInstance().getDungeonFighterConfig().getNotEnoughMoneyMessage());
            fighter.getPlayer().playSound(fighter.getPlayer().getEyeLocation(), Sound.NOTE_BASS, 100, 1);
            return false;
        }
        fighter.spendMoney(cost);
        return true;
    }
}
