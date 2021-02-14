package fr.heavenmoon.factions.listeners.enderchest;

import fr.heavenmoon.factions.HeavenFactions;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class EnderChestListener implements Listener {

    private final HeavenFactions plugin;

    public EnderChestListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getInventory().getName().contains("EnderChest de " + player.getName() + " N°")) {
            player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 1.0F, 1.0F);
            plugin.getEnderChestManager().update(player, event.getInventory());
        }
    }
}
