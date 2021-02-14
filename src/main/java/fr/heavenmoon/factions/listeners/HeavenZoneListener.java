package fr.heavenmoon.factions.listeners;

import fr.heavenmoon.factions.HeavenFactions;
import fr.moon.core.bukkit.format.Message;
import fr.moon.core.common.format.message.PrefixType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HeavenZoneListener implements Listener {

    private final HeavenFactions plugin;

    public HeavenZoneListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreakEvent(BlockBreakEvent event) {

        Player player = event.getPlayer();

        if (plugin.getHeavenZoneManager().containsPlayer(player)) {
            event.setCancelled(true);
            new Message(PrefixType.ERROR, "Vous ne pouvez pas casser de block à l'intérieur d'une zone protégée.").send(player);
        }
    }

    @EventHandler
    public void onBreakEvent(BlockPlaceEvent event) {

        Player player = event.getPlayer();

        if (plugin.getHeavenZoneManager().containsPlayer(player)) {
            event.setCancelled(true);
            new Message(PrefixType.ERROR, "Vous ne pouvez pas poser de block à l'intérieur d'une zone protégée.").send(player);
        }
    }

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (plugin.getHeavenZoneManager().containsPlayer(damager)) {
                event.setCancelled(true);
                new Message(PrefixType.ERROR, "Le PVP est désactivé dans cette zone.").send(damager);
            }
        }
    }
}
