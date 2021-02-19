package fr.heavenmoon.factions.commands;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.koth.KothState;
import fr.heavenmoon.factions.utils.LocationUtils;
import fr.heavenmoon.core.bukkit.format.Message;

import fr.heavenmoon.core.common.format.FormatUtils;
import fr.heavenmoon.core.common.format.message.MessageType;
import fr.heavenmoon.core.common.format.message.PrefixType;
import fr.heavenmoon.persistanceapi.customs.factions.CustomFaction;
import fr.heavenmoon.persistanceapi.customs.player.CustomPlayer;
import fr.heavenmoon.persistanceapi.customs.player.data.RankList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class KothCommand implements CommandExecutor {

    private final HeavenFactions plugin;
    private final String syntax = "/koth join";

    public KothCommand(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            new Message(MessageType.CONSOLE).send(sender);
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("join")) {
                if (!KothState.isState(KothState.STARTING)) {
                    new Message(PrefixType.ERROR, "L'event n'est pas lancé ou est déjà commencé.").send(player);
                    return false;
                }

                if (plugin.getKothManager().isAlreadyInKoth(player)) {
                    new Message(PrefixType.ERROR, "Vous faîtes déjà partie du KOTH.").send(player);
                    return false;
                }

                CustomFaction customFaction =
                        plugin.getPersistanceManager().getfPlayersManager().getFactionPlayer(player.getUniqueId()).getCustomFaction();
                plugin.getKothManager().addFaction(customFaction);
                plugin.getKothManager().getKothFaction(customFaction).ifPresent(kothFaction -> kothFaction.getKothPlayers().add(player));
                Arrays.asList(FormatUtils.graySpacer(), "Vous avez rejoint l'event KOTH !", "Veuillez vous rendre aux coordonnées prévues...", FormatUtils.graySpacer()).forEach(player::sendMessage);

                return true;
            } else {
                new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(player);
            }
        } else {
            new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(player);
        }

        if (args.length == 2) {
            CustomPlayer customPlayer = plugin.getPersistanceManager().getPlayerManager().getCustomPlayer(player.getUniqueId());
            if (customPlayer.hasPermission(RankList.ADMINISTRATEUR)) {
                new Message(MessageType.PERMISSION).send(sender);
                return false;
            }

            if (args[0].equalsIgnoreCase("setspawn")) {
                plugin.getConfig().set("koth.region." + args[1], LocationUtils.loc2str(player.getLocation()));
                plugin.saveConfig();
            } else {
                new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(player);
            }
        } else {
            new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(player);
        }

        return false;
    }
}
