package fr.heavenmoon.factions.commands.home;

import fr.heavenmoon.factions.HeavenFactions;
import fr.moon.core.bukkit.format.Message;
import fr.moon.core.common.data.player.CustomPlayer;
import fr.moon.core.common.data.player.rank.RankList;
import fr.moon.core.common.format.message.MessageType;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    private final HeavenFactions plugin;
    private final RankList rank = RankList.MODERATEUR;
    private final String syntax = "/home ou /home <home>";

    public HomeCommand(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            new Message(MessageType.CONSOLE).send(sender);
            return false;
        }
        Player player = (Player) sender;
        FactionPlayer factionPlayer = plugin.getfPlayersManager().get(player);
        CustomPlayer customPlayer = plugin.getApi().getCommons().getPlayerManager().get(player.getName(), player.getUniqueId().toString());

        if (args.length == 0) {
            plugin.getHomesManager().getHomes(player, factionPlayer);
        } else if (args.length == 1) {
            if (args[0].length() > 2 && args[0].substring(0, 2).equalsIgnoreCase("p:") && customPlayer.hasOnlyPermission(rank)) {
                FactionPlayer factionTarget = plugin.getfPlayersManager().get(args[0].substring(2));
                plugin.getHomesManager().getHomes(player, factionTarget);
            } else {
                plugin.getHomesManager().teleportToHome(player, factionPlayer, args[0]);
            }

        } else if (args.length == 2) {
            if (!customPlayer.hasOnlyPermission(rank)) {
                new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(player);
                return false;
            }
            FactionPlayer factionTarget = plugin.getfPlayersManager().get(args[0]);
            plugin.getHomesManager().teleportToHome(player, factionTarget, args[1]);
        } else {
            new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(player);
        }
        return false;
    }
}
