package fr.heavenmoon.factions.commands.spawn;

import fr.heavenmoon.factions.HeavenFactions;
import fr.moon.core.bukkit.MoonBukkitCore;
import fr.moon.core.bukkit.format.Message;
import fr.moon.core.common.data.player.CustomPlayer;
import fr.moon.core.common.data.player.rank.RankList;
import fr.moon.core.common.format.message.MessageType;
import fr.moon.core.common.format.message.PrefixType;
import fr.moon.core.common.utils.LocationsUtils;
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
        CustomPlayer customPlayer = MoonBukkitCore.get().getCommons().getPlayerManager().get(player.getName(), player.getUniqueId().toString());
        if (!customPlayer.hasOnlyPermission(rank)) {
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
