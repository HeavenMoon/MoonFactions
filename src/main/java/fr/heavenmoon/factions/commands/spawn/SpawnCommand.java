package fr.heavenmoon.factions.commands.spawn;

import fr.heavenmoon.core.bukkit.format.Message;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.core.bukkit.utils.ActionbarBuilder;
import fr.heavenmoon.core.common.format.message.MessageType;
import fr.heavenmoon.core.common.format.message.PrefixType;
import fr.heavenmoon.core.common.utils.LocationsUtils;
import fr.heavenmoon.core.common.utils.wrappers.LambdaWrapper;
import fr.heavenmoon.persistanceapi.customs.player.CustomPlayer;
import fr.heavenmoon.persistanceapi.customs.player.data.RankList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SpawnCommand implements CommandExecutor
{
	
	private final HeavenFactions plugin;
	private final String syntax = "/spawn";
	private final Map<Player, Integer> tpCooldowns;
	
	public SpawnCommand(HeavenFactions plugin)
	{
		this.plugin = plugin;
		tpCooldowns = new HashMap<>();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
	{
		if (!(sender instanceof Player))
		{
			new Message(MessageType.CONSOLE).send(sender);
			return false;
		}
		Player player = (Player) sender;
		
		if (args.length != 0)
		{
			new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(sender);
			return false;
		}
		CustomPlayer customPlayer = plugin.getPersistanceManager().getPlayerManager().getCustomPlayer(player.getUniqueId());
		
		if (!customPlayer.canTp())
		{
			new Message(PrefixType.ERROR, "Vous ne pouvez pas vous téléporter maintenant !");
			return false;
		}
		startCooldownTp(player, player.getLocation(), 5);
		
		return false;
	}
	
	public void startCooldownTp(Player player, Location loc, int seconds)
	{
		LambdaWrapper<Integer> cooldownTime = new LambdaWrapper<>(seconds);
		
		CustomPlayer customPlayer = plugin.getPersistanceManager().getPlayerManager().getCustomPlayer(player.getUniqueId());
		customPlayer.setCanTp(false);
		if (customPlayer.hasPermission(RankList.MODERATEUR))
		{
			player.teleport(LocationsUtils.stringToLocation(plugin.getConfig().getString("spawn")));
			return;
		}
		int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () ->
		{
			new ActionbarBuilder(ChatColor.GRAY + "Téléportation dans " + ChatColor.LIGHT_PURPLE + cooldownTime.getData() + ChatColor.GRAY +
					" seconde(s)...", 2).send(player);
			cooldownTime.setData(cooldownTime.getData() - 1);
			if (cooldownTime.getData() == 0)
			{
				customPlayer.setCanTp(true);
				Bukkit.getScheduler().cancelTask(tpCooldowns.get(player));
				player.teleport(LocationsUtils.stringToLocation(plugin.getConfig().getString("spawn")));
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
