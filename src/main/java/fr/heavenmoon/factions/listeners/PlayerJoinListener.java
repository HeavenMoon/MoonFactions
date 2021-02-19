package fr.heavenmoon.factions.listeners;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.enderchest.EnderChest;
import fr.heavenmoon.factions.utils.BukkitSerialization;
import fr.heavenmoon.persistanceapi.customs.factions.FactionPlayer;
import fr.heavenmoon.persistanceapi.customs.player.CustomPlayer;
import fr.heavenmoon.core.bukkit.scoreboard.ScoreboardTeam;
import fr.heavenmoon.core.common.utils.LocationsUtils;
import fr.heavenmoon.persistanceapi.customs.player.data.RankList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class PlayerJoinListener implements Listener
{
	
	private final HeavenFactions plugin;
	
	public PlayerJoinListener(HeavenFactions plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void on(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		CustomPlayer customPlayer = plugin.getPersistanceManager().getPlayerManager().getCustomPlayer(player.getUniqueId());
		FactionPlayer factionPlayer = plugin.getPersistanceManager().getfPlayersManager().getFactionPlayer(player.getUniqueId());
		
		// Serveur
		if (!customPlayer.getModerationData().isVanish())
		{
			String join = customPlayer.hasPermission(RankList.GUIDE) ?
					ChatColor.getByChar(customPlayer.getRankData().getStyleCode()) + customPlayer.getRankData().getPrefix() + customPlayer.getName() : customPlayer.getName();
			Bukkit.broadcastMessage("§8[§a+§8] §a" + join);
		}
		
		// Cache
		if (!factionPlayer.isExist())
		{
			factionPlayer.setExist(true);
			factionPlayer.setFirstLogin(System.currentTimeMillis());
			
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
					() -> player.teleport(LocationsUtils.stringToLocation(plugin.getConfig().getString("spawn"))), 1L);
			
		}
		factionPlayer.setName(player.getName());
		factionPlayer.setLastLogin(System.currentTimeMillis());
		plugin.getPersistanceManager().getfPlayersManager().commit(factionPlayer);
		
		player.setGameMode(GameMode.getByValue(customPlayer.getGamemode()));
		
		File file = new File(plugin.getDataFolder() + "/EnderChest/" + player.getUniqueId().toString() + ".json");
		if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
		
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			plugin.getEnderChestManager().save(new EnderChest(BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9 * 3)),
					BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9 * 3)),
					BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9 * 3)),
					BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9 * 3)),
					BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9 * 3)),
					BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9 * 3)),
					BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9 * 3)),
					BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9 * 3)),
					BukkitSerialization.toBase64(Bukkit.getServer().createInventory(null, 9 * 3))), player);
		}
		
		plugin.getCrateManager().loadPlayer(player);
		
	}
}