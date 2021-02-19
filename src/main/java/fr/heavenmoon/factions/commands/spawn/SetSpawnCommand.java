package fr.heavenmoon.factions.commands.spawn;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.core.bukkit.MoonBukkitCore;
import fr.heavenmoon.core.bukkit.format.Message;
import fr.heavenmoon.core.common.format.message.MessageType;
import fr.heavenmoon.core.common.format.message.PrefixType;
import fr.heavenmoon.core.common.utils.LocationsUtils;
import fr.heavenmoon.persistanceapi.customs.player.CustomPlayer;
import fr.heavenmoon.persistanceapi.customs.player.data.RankList;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    private final HeavenFactions plugin;
    private final RankList rank = RankList.ADMINISTRATEUR;
    private final String syntax = "/setspawn";

    public SetSpawnCommand(HeavenFactions plugin) {
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
        if (!customPlayer.hasPermission(rank)) {
            new Message(MessageType.PERMISSION).send(player);
            return false;
        }

        if (args.length != 0) {
            new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(player);
            return false;
        }

        Location loc = player.getLocation();
        plugin.getConfig().set("spawn", LocationsUtils.locationToString(loc));
        plugin.saveConfig();
        new Message(PrefixType.ADMIN, "Nouveau spawn appliqué.").send(player);
        return false;
    }
}
