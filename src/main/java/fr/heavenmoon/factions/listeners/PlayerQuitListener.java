package fr.heavenmoon.factions.listeners;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.persistanceapi.customs.factions.FactionPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerQuitListener implements Listener {

    private final HeavenFactions plugin;

    public PlayerQuitListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FactionPlayer factionPlayer = plugin.getPersistanceManager().getfPlayersManager().getFactionPlayer(player.getUniqueId());
        
        long timePlayed = System.currentTimeMillis() / 1000L - factionPlayer.getLastLogin() / 1000L;
        factionPlayer.setTimePlayed(timePlayed);
        
        plugin.getPersistanceManager().getfPlayersManager().update(factionPlayer);
        plugin.getPersistanceManager().getfPlayersManager().remove(factionPlayer);
        
    }
}