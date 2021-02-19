package fr.heavenmoon.factions.listeners;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.persistanceapi.customs.factions.FactionPlayer;
import fr.heavenmoon.persistanceapi.customs.player.CustomPlayer;
import fr.heavenmoon.persistanceapi.customs.player.data.RankList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final HeavenFactions plugin;

    public PlayerQuitListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        
        Player player = event.getPlayer();
        CustomPlayer customPlayer = plugin.getPersistanceManager().getPlayerManager().getCustomPlayer(player.getUniqueId());
    
        // Serveur
        if (!customPlayer.getModerationData().isVanish())
        {
            String join = customPlayer.hasPermission(RankList.GUIDE) ?
                    ChatColor.getByChar(customPlayer.getRankData().getStyleCode()) + customPlayer.getRankData().getPrefix() + customPlayer.getName() : customPlayer.getName();
            Bukkit.broadcastMessage("§8[§c-§8] §c" + join);
        }
        
        FactionPlayer factionPlayer = plugin.getPersistanceManager().getfPlayersManager().getFactionPlayer(player.getUniqueId());
        
        long timePlayed = System.currentTimeMillis() / 1000L - factionPlayer.getLastLogin() / 1000L;
        factionPlayer.setTimePlayed(timePlayed);
        
        plugin.getPersistanceManager().getfPlayersManager().update(factionPlayer);
        plugin.getPersistanceManager().getfPlayersManager().remove(factionPlayer);
        
    }
}