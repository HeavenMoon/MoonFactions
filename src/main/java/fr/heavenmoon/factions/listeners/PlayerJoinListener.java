package fr.heavenmoon.factions.listeners;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.enderchest.EnderChest;
import fr.heavenmoon.factions.utils.BukkitSerialization;
import fr.moon.core.bukkit.scoreboard.ScoreboardTeam;
import fr.moon.core.common.data.player.CustomPlayer;
import fr.moon.core.common.utils.LocationsUtils;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class PlayerJoinListener implements Listener {

    private final HeavenFactions plugin;

    public PlayerJoinListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void on(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CustomPlayer customPlayer = plugin.getApi().getCommons().getPlayerManager().get(player.getName(), player.getUniqueId().toString());
        player.setGameMode(GameMode.getByValue(customPlayer.getCore().getGamemode()));
        if (!player.hasPlayedBefore()) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> player.teleport(LocationsUtils.stringToLocation(plugin.getConfig().getString("spawn"))), 1L);
            //TODO: cinematic
        }

        File file = new File(plugin.getDataFolder() + "/EnderChest/" + player.getUniqueId().toString() + ".json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            plugin.getEnderChestManager().save(new EnderChest(BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9*3)),
                    BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9*3)),
                    BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9*3)),
                    BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9*3)),
                    BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9*3)),
                    BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9*3)),
                    BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9*3)),
                    BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9*3)),
                    BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9*3))), player);
        }

        FactionPlayer factionPlayer = plugin.getfPlayersManager().get(player);
        plugin.getfPlayersManager().commit(factionPlayer);

        for (ScoreboardTeam team : plugin.getApi().getTeams()) {
            (((CraftPlayer) Bukkit.getPlayer(event.getPlayer().getUniqueId())).getHandle()).playerConnection.sendPacket(team.createTeam());
        }
        plugin.getScoreboardManager().onLogin(event.getPlayer());
        plugin.getCrateManager().loadPlayer(player);

        System.out.println(factionPlayer.getQuestData().getPourcentOfProgress());
        System.out.println(factionPlayer.getQuestData().pourcentToString());
    }
}