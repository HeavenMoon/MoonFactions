package fr.heavenmoon.factions.commands.home;

import fr.heavenmoon.core.bukkit.format.Message;
import fr.heavenmoon.core.bukkit.utils.BUniqueID;
import fr.heavenmoon.core.common.format.message.MessageType;
import fr.heavenmoon.factions.HeavenFactions;

import fr.heavenmoon.persistanceapi.customs.factions.FactionPlayer;
import fr.heavenmoon.persistanceapi.customs.player.CustomPlayer;
import fr.heavenmoon.persistanceapi.customs.player.data.RankList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SethomeCommand implements CommandExecutor
{
	
	private final HeavenFactions plugin;
	private final RankList rank = RankList.MODERATEUR;
	private final String syntax = "/home ou /home <home>";
	
	public SethomeCommand(HeavenFactions plugin)
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
		
		if (args.length == 1)
		{
			plugin.getHomesManager().addHomes(player, factionPlayer, args[0], player.getLocation());
		}
		else if (args.length == 2)
		{
			if (!customPlayer.hasPermission(rank))
			{
				new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(player);
				return false;
			}
			FactionPlayer factionTarget = plugin.getPersistanceManager().getfPlayersManager().getFactionPlayer(BUniqueID.get(args[0]));
			plugin.getHomesManager().addHomes(player, factionTarget, args[1], player.getLocation());
		}
		else
		{
			new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(player);
		}
		
		
		return false;
	}
}
