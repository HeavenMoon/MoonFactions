package fr.heavenmoon.factions.commands;

import fr.heavenmoon.factions.HeavenFactions;
import fr.moon.core.bukkit.format.Message;
import fr.moon.core.common.data.player.CustomPlayer;
import fr.moon.core.common.data.player.rank.RankList;
import fr.moon.core.common.format.message.MessageType;
import fr.moon.core.common.format.message.PrefixType;
import fr.moon.core.common.utils.math.MathUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCrateCommand implements CommandExecutor {

    private final HeavenFactions plugin;
    private final String syntax = "/setcrate <number>";

    public SetCrateCommand(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            new Message(MessageType.CONSOLE).send(sender);
            return false;
        }

        Player player = (Player) sender;

        CustomPlayer customPlayer = plugin.getApi().getCommons().getPlayerManager().get(player.getName(), player.getUniqueId().toString());
        if (!customPlayer.hasOnlyPermission(RankList.ADMINISTRATEUR)) {
            new Message(MessageType.PERMISSION).send(sender);
            return false;
        }

        if (args.length == 1) {
            if(!MathUtils.isInteger(args[0])) {
                new Message(PrefixType.ERROR, "Le nombre est invalide.").send(player);
                return false;
            }

            int number = Integer.parseInt(args[0]);
            if (plugin.getConfig().isSet("crates." + number)) {
                new Message(PrefixType.ERROR, "Le nombre " + number + " est déjà défini.").send(player);
                return false;
            }

            Location playerLocation = player.getLocation();
            Location location = new Location(playerLocation.getWorld(), Math.round(playerLocation.getX()), Math.round(playerLocation.getY()), Math.round(playerLocation.getZ()));

            plugin.getCrateManager().addCrateBox(location, number);
        } else {
            new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(player);
        }

        return false;
    }
}
