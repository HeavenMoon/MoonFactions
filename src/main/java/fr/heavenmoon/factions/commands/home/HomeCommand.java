package fr.heavenmoon.factions.commands.home;

import fr.heavenmoon.core.bukkit.utils.BUniqueID;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.core.bukkit.format.Message;
import fr.heavenmoon.core.common.format.message.MessageType;
import fr.heavenmoon.persistanceapi.customs.factions.FactionPlayer;
import fr.heavenmoon.persistanceapi.customs.player.CustomPlayer;
import fr.heavenmoon.persistanceapi.customs.player.data.RankList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor
{
	
	private final HeavenFactions plugin;
	private final RankList rank = RankList.MODERATEUR;
	private final String syntax = "/home ou /home <home>";
	
	public HomeCommand(HeavenFactions plugin)
	{
		this.plugin = plugin;
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
		FactionPlayer factionPlayer = plugin.getPersistanceManager().getfPlayersManager().getFactionPlayer(player.getUniqueId());
		CustomPlayer customPlayer = plugin.getPersistanceManager().getPlayerManager().getCustomPlayer(player.getUniqueId());
		
		if (args.length == 0)
		{
			plugin.getHomesManager().getHomes(player, factionPlayer);
		}
		else if (args.length == 1)
		{
			if (args[0].length() > 2 && args[0].substring(0, 2).equalsIgnoreCase("p:") && customPlayer.hasPermission(rank))
			{
				FactionPlayer factionTarget =
						plugin.getPersistanceManager().getfPlayersManager().getFactionPlayer(BUniqueID.get(args[0].substring(2)));
				plugin.getHomesManager().getHomes(player, factionTarget);
			}
			else
			{
				plugin.getHomesManager().teleportToHome(player, factionPlayer, args[0]);
			}
			
		}
		else if (args.length == 2)
		{
			if (!customPlayer.hasPermission(rank))
			{
				new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(player);
				return false;
			}
			FactionPlayer factionTarget = plugin.getPersistanceManager().getfPlayersManager().getFactionPlayer(BUniqueID.get(args[0]));
			plugin.getHomesManager().teleportToHome(player, factionTarget, args[1]);
		}
		else
		{
			new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(player);
		}
		return false;
	}
}
