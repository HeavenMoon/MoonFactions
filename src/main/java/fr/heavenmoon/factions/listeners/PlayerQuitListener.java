package fr.heavenmoon.factions.listeners;

import fr.heavenmoon.factions.HeavenFactions;
import fr.moon.core.bukkit.scoreboard.ScoreboardTeam;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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
        FactionPlayer factionPlayer = plugin.getfPlayersManager().get(player);
        plugin.getfPlayersManager().update(factionPlayer);
        plugin.getfPlayersManager().remove(factionPlayer);

        for (ScoreboardTeam team : plugin.getApi().getTeams())
            (((CraftPlayer) event.getPlayer()).getHandle()).playerConnection.sendPacket(team.removeTeam());
        plugin.getScoreboardManager().onLogout(event.getPlayer());
    }
}