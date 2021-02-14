package fr.heavenmoon.factions.listeners;

import fr.heavenmoon.factions.HeavenFactions;
import fr.moon.core.common.utils.builders.items.HeadBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PlayerDeathListener implements Listener {

    private final HeavenFactions plugin;

    public PlayerDeathListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity();
            Random random = new Random();
            int luck = random.nextInt(4);
            if (luck == 4) {
                ItemStack playerHead = new HeadBuilder()
                        .setOwner(player.getName())
                        .setName(ChatColor.GRAY + "Tête de " + ChatColor.DARK_PURPLE + player.getName())
                        .setLore(ChatColor.GRAY + "Mort vaillamment au combat par " + ChatColor.LIGHT_PURPLE + player.getKiller().getName())
                        .build();
                player.getWorld().dropItemNaturally(player.getLocation(), playerHead);
            }
        }

        event.getEntity().spigot().respawn();
    }

}
