package fr.heavenmoon.factions.listeners.crate;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.crates.CrateGUI;
import fr.heavenmoon.factions.crates.CrateKey;
import fr.heavenmoon.factions.crates.CrateUnit;
import fr.heavenmoon.factions.enderchest.EnderChestGui;
import fr.moon.core.bukkit.MoonBukkitCore;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CrateListener implements Listener {

    private final HeavenFactions plugin;

    public CrateListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock.getType() == Material.ENDER_CHEST) {
                Location location = new Location(clickedBlock.getLocation().getWorld(), Math.round(clickedBlock.getLocation().getX()), Math.round(clickedBlock.getLocation().getY()), Math.round(clickedBlock.getLocation().getZ()));
                if (plugin.getCrateManager().containsLocation(location)) {
                    Player player = event.getPlayer();
                    ItemStack item = event.getItem();
                    if (item != null) {
                        String displayName = item.getItemMeta().getDisplayName();
                        if (item.getType() == Material.TRIPWIRE_HOOK && displayName.startsWith(ChatColor.GRAY + "Clé pour")) {
                            CrateUnit crate = CrateUnit.getCrateByName(displayName.substring(13));
                            player.getInventory().removeItem(new CrateKey(crate).toItemStack());
                            event.setCancelled(true);
                            plugin.getApi().getGuiManager().openGui(player, new CrateGUI(plugin, crate));
                        }
                    }
                } else {
                    event.setCancelled(true);
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                    plugin.getApi().getGuiManager().openGui(event.getPlayer(),new EnderChestGui(MoonBukkitCore.get()));
                }
            }
        }
    }
}
