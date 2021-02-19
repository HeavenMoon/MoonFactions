package fr.heavenmoon.factions.commands;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.core.bukkit.format.Message;
import fr.heavenmoon.core.common.format.message.MessageType;
import fr.heavenmoon.core.common.format.message.PrefixType;
import fr.heavenmoon.persistanceapi.customs.player.CustomPlayer;
import fr.heavenmoon.persistanceapi.customs.player.data.RankList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HeavenZoneCommand implements CommandExecutor {

    private final HeavenFactions plugin;
    private final String syntax = "/setheavenzone [name_zone] [1-2]";
    //setheavenzone name loc1/loc2
    //setheavenzone name pvp true/false

    public HeavenZoneCommand(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            new Message(MessageType.CONSOLE).send(sender);
            return false;
        }

        Player player = (Player) sender;

        CustomPlayer customPlayer = plugin.getPersistanceManager().getPlayerManager().getCustomPlayer(player.getUniqueId());
        if (customPlayer.hasPermission(RankList.ADMINISTRATEUR)) {
            new Message(MessageType.PERMISSION).send(sender);
            return false;
        }

        if (args.length == 2) {
            plugin.getHeavenZoneManager().addLocationForHeavenZone(args[0], args[1], player.getLocation());
            new Message(PrefixType.SERVER, "Vous avez set la location " + args[1].substring(2) + " à " + args[0]);
        } else if (args.length == 3) {
            plugin.getHeavenZoneManager().setPvpForHeavenZone(args[0], Boolean.parseBoolean(args[2]));
            new Message(PrefixType.SERVER, "Vous avez set le pvp à " + args[2]);
        } else {
            new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(player);
        }

        return false;
    }
}
