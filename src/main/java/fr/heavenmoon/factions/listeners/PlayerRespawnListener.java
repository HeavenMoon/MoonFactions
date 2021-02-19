package fr.heavenmoon.factions.listeners;

import fr.heavenmoon.core.common.utils.LocationsUtils;
import fr.heavenmoon.factions.HeavenFactions;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    private final HeavenFactions plugin;

    public PlayerRespawnListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerRespawnEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                event.getPlayer().teleport(LocationsUtils.stringToLocation(plugin.getConfig().getString("spawn")));
            }
        }, 5L);
    }
}
