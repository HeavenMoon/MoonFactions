package fr.heavenmoon.factions.homes;

import fr.heavenmoon.core.bukkit.MoonBukkitCore;
import fr.heavenmoon.core.bukkit.format.Message;
import fr.heavenmoon.core.bukkit.utils.ActionbarBuilder;
import fr.heavenmoon.core.common.format.message.PrefixType;
import fr.heavenmoon.core.common.utils.wrappers.LambdaWrapper;
import fr.heavenmoon.persistanceapi.customs.factions.CustomHome;
import fr.heavenmoon.persistanceapi.customs.factions.FactionPlayer;
import fr.heavenmoon.persistanceapi.customs.player.CustomPlayer;
import fr.heavenmoon.persistanceapi.customs.player.data.RankList;

import fr.heavenmoon.factions.HeavenFactions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomesManager
{
	
	private final HeavenFactions plugin;
	private final RankList rank = RankList.ADMINISTRATEUR;
	private final Map<Player, Integer> tpCooldowns;
	
	public HomesManager(HeavenFactions plugin)
	{
		this.plugin = plugin;
		tpCooldowns = new HashMap<>();
	}
	
	public List<CustomHome> getHomesOfPlayer(FactionPlayer player)
	{
		return player.getHomes();
	}
	
	public void getHomes(Player sender, FactionPlayer target)
	{
		if (target.getHomes().isEmpty())
		{
			if (sender.getName().equals(target.getName()))
			{
				new Message(PrefixType.SERVER, "Vous n'avez pas de home.").send(sender);
			}
			else
			{
				new Message(PrefixType.MODO, ChatColor.GRAY + target.getName() + ChatColor.LIGHT_PURPLE + " n'as pas de home.")
						.send(sender);
			}
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (CustomHome home : target.getHomes())
		{
			sb.append(ChatColor.AQUA + home.getName()).append(ChatColor.GRAY + ", ");
		}
		String message = sb.toString();
		if (sender.getName().equals(target.getName()))
		{
			new Message(PrefixType.SERVER, "Vos homes: " + ChatColor.AQUA + message.substring(0, sb.toString().length() - 2)).send(sender);
		}
		else
		{
			new Message(PrefixType.MODO, "Home de " + ChatColor.GRAY + target.getName() + ChatColor.LIGHT_PURPLE + ": " +
					message.substring(0, sb.toString().length() - 2)).send(sender);
		}
	}
	
	public void addHomes(Player sender, FactionPlayer target, String homeName, Location location)
	{
		CustomPlayer customPlayer = plugin.getPersistanceManager().getPlayerManager().getCustomPlayer(sender.getUniqueId());
		if (!customPlayer.hasPermission(RankList.MODERATEUR))
		{
			if ((customPlayer.hasPermission("home.1") && target.getHomes().size() >= 1) || (customPlayer.hasPermission("home.2") && target.getHomes().size() >= 2)
					|| (customPlayer.hasPermission("home.3") && target.getHomes().size() >= 3) || (customPlayer.hasPermission("home.4") && target.getHomes().size() >= 4)
					|| (customPlayer.hasPermission("home.5") && target.getHomes().size() >= 1) || (customPlayer.hasPermission("home.5") && target.getHomes().size() >= 1))
			{
				new Message(PrefixType.ERROR, "Vous ne pouvez pas avoir plus de home.").send(sender);
				return;
			}
		}
		
		if (exist(target, homeName))
		{
			if (sender.getName().equals(target.getName()))
			{
				new Message(PrefixType.SERVER, "Vous avez déjà un home avec ce nom.").send(sender);
			}
			else
			{
				new Message(PrefixType.MODO, ChatColor.GRAY + sender.getName() + ChatColor.LIGHT_PURPLE + " a déjà un home avec ce nom.")
						.send(sender);
			}
			return;
		}
		target.getHomes().add(new CustomHome(homeName, location.getWorld().getName(), location.getX(), location.getY(), location.getZ(),
				location.getYaw(), location.getPitch()));
		plugin.getPersistanceManager().getfPlayersManager().commit(target);
		if (sender.getName().equals(target.getName()))
		{
			new Message(PrefixType.SERVER, "Home " + ChatColor.AQUA + homeName + ChatColor.GRAY + " crée.").send(sender);
		}
		else
		{
			new Message(PrefixType.MODO,
					"Home " + ChatColor.GREEN + homeName + ChatColor.LIGHT_PURPLE + " crée pour " + ChatColor.GRAY + target.getName())
					.send(sender);
		}
	}
	
	public void removeHomes(Player sender, FactionPlayer target, String homeName)
	{
		if (!exist(target, homeName))
		{
			if (sender.getName().equals(target.getName()))
			{
				new Message(PrefixType.SERVER, "Vous n'avez pas de home avec ce nom.").send(sender);
			}
			else
			{
				new Message(PrefixType.MODO, ChatColor.GRAY + target.getName() + ChatColor.LIGHT_PURPLE + " n'a pas de home avec ce nom.")
						.send(sender);
			}
			return;
		}
		target.getHomes().remove(get(target, homeName));
		plugin.getPersistanceManager().getfPlayersManager().commit(target);
		if (sender.getName().equals(target.getName()))
		{
			new Message(PrefixType.SERVER, "Home " + ChatColor.AQUA + homeName + ChatColor.GRAY + " supprimé.").send(sender);
		}
		else
		{
			new Message(PrefixType.MODO,
					"Home " + ChatColor.GREEN + homeName + ChatColor.LIGHT_PURPLE + " supprimé pour " + ChatColor.GRAY + target.getName())
					.send(sender);
		}
		
	}
	
	public void teleportToHome(Player sender, FactionPlayer target, String homeName)
	{
		if (exist(target, homeName))
		{
			startCooldownTp(sender, sender.getLocation(), 5, new Location(Bukkit.getWorld(get(target, homeName).getWorldName()),
					get(target, homeName).getX(), get(target, homeName).getY(), get(target, homeName).getZ(),
					get(target, homeName).getYaw(), get(target, homeName).getPitch()));
			return;
		}
		
		if (sender.getName().equals(target.getName()))
		{
			new Message(PrefixType.SERVER, "Vous n'avez pas de home intitulé " + ChatColor.AQUA + homeName + ChatColor.GRAY + ".")
					.send(sender);
		}
		else
		{
			new Message(PrefixType.MODO,
					ChatColor.GRAY + target.getName() + ChatColor.LIGHT_PURPLE + " n'a pas de home intitulé " + ChatColor.GREEN + homeName +
							ChatColor.LIGHT_PURPLE + ".").send(sender);
		}
	}
	
	public boolean exist(FactionPlayer target, String homeName)
	{
		for (CustomHome home : target.getHomes())
		{
			if (home.getName().equalsIgnoreCase(homeName)) return true;
		}
		return false;
	}
	
	public CustomHome get(FactionPlayer target, String homeName)
	{
		for (CustomHome home : target.getHomes())
		{
			if (home.getName().equalsIgnoreCase(homeName)) return home;
		}
		return null;
	}
	
	public void startCooldownTp(Player player, Location loc, int seconds, Location dest)
	{
		LambdaWrapper<Integer> cooldownTime = new LambdaWrapper<>(seconds);
		CustomPlayer customPlayer = plugin.getPersistanceManager().getPlayerManager().getCustomPlayer(player.getUniqueId());
		
		customPlayer.setCanTp(false);
		if (customPlayer.hasPermission(RankList.MODERATEUR))
		{
			player.teleport(dest);
			return;
		}
		int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () ->
		{
			new ActionbarBuilder(
					"Téléportation dans " + ChatColor.LIGHT_PURPLE + cooldownTime.getData() + ChatColor.GRAY + " seconde(s)...", 2)
					.send(player);
			cooldownTime.setData(cooldownTime.getData() - 1);
			if (cooldownTime.getData() == 0)
			{
				customPlayer.setCanTp(true);
				Bukkit.getScheduler().cancelTask(tpCooldowns.get(player));
				player.teleport(dest);
				return;
			}
			if (player.getLocation().getX() != loc.getX() || player.getLocation().getY() != loc.getY() ||
					player.getLocation().getZ() != loc.getZ())
			{
				new Message(PrefixType.ERROR, "Vous vous êtes déplacé ce qui a annulé votre téléportation.").send(player);
				customPlayer.setCanTp(true);
				Bukkit.getScheduler().cancelTask(tpCooldowns.get(player));
			}
			
		}, 0L, 20L);
		tpCooldowns.put(player, scheduler);
	}
}

