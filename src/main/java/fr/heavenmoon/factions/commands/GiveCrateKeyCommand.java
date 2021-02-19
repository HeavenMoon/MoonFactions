package fr.heavenmoon.factions.commands;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.crates.CrateKey;
import fr.heavenmoon.factions.crates.CrateUnit;
import fr.heavenmoon.persistanceapi.customs.player.CustomPlayer;
import fr.heavenmoon.core.bukkit.format.Message;
import fr.heavenmoon.core.common.format.message.MessageType;
import fr.heavenmoon.core.common.format.message.PrefixType;
import fr.heavenmoon.core.common.utils.math.MathUtils;
import fr.heavenmoon.persistanceapi.customs.player.data.RankList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class GiveCrateKeyCommand implements CommandExecutor {

    private final HeavenFactions plugin;
    private final String syntax = "/givekey pseudo crate amount";

    public GiveCrateKeyCommand(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
            CustomPlayer customPlayer = plugin.getPersistanceManager().getPlayerManager().getCustomPlayer(player.getUniqueId());
            if (!customPlayer.hasPermission(RankList.ADMINISTRATEUR)) {
                new Message(MessageType.PERMISSION).send(sender);
                return false;
            }
        }

        if (args.length == 3) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                new Message(MessageType.NO_PLAYER).send(sender);
                return false;
            }

            CrateUnit crate = CrateUnit.getCrateByName(args[1]);
            if (crate == null) {
                new Message(PrefixType.ERROR, ChatColor.RED + "Précisez un nom correct :").send(sender);
                Arrays.stream(CrateUnit.values()).forEach(crateUnit -> new Message(ChatColor.RED + crateUnit.getCrate().getName()).send(sender));
                return false;
            }

            if (!MathUtils.isInteger(args[2])) {
                new Message(PrefixType.ERROR, "Précisez un montant valide.").send(sender);
            }

            CrateKey key = new CrateKey(crate);
            target.getInventory().addItem(key.toItemStack());
            new Message(ChatColor.GRAY + "Vous venez de recevoir une " + key.getName()).send(target);
            new Message(ChatColor.GRAY + "Vous avez envoyé une " + key.getName() + ChatColor.GRAY + " à " + target.getName()).send(sender);
        } else {
            new Message(MessageType.SYNTAXE, "%syntax%", syntax).send(sender);
        }

        return false;
    }
}
