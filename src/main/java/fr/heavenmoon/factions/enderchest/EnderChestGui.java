package fr.heavenmoon.factions.enderchest;

import fr.heavenmoon.core.bukkit.gui.AbstractGui;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.utils.BukkitSerialization;
import fr.heavenmoon.core.bukkit.MoonBukkitCore;
import fr.heavenmoon.core.common.utils.builders.items.ItemBuilder;
import fr.heavenmoon.persistanceapi.customs.player.CustomPlayer;
import fr.heavenmoon.persistanceapi.customs.player.data.RankList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class EnderChestGui extends AbstractGui
{

    public EnderChestGui(MoonBukkitCore plugin) {
        super(plugin);
    }

    @Override
    public void display(Player player) {
        this.inventory = plugin.getServer().createInventory(null, 9, ChatColor.DARK_PURPLE + "EnderChest de " + player.getName());
        CustomPlayer customPlayer = plugin.getCommons().getPersistanceManager().getPlayerManager().getCustomPlayer(player.getUniqueId());
        for (int i = 1; i < 10; i++) {
            setSlotData(ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°" + i, new ItemBuilder().setMaterial(Material.ENDER_CHEST).toItemStack(), i - 1, null, "ender" + i);

            if (i == 2 && !customPlayer.hasPermission("enderchest.2") && !customPlayer.hasPermission(RankList.MODERATEUR)) {
                setSlotData(ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°" + i, new ItemBuilder().setMaterial(Material.BARRIER).toItemStack(), i - 1, null, null);
            }
            if (i == 3 && !customPlayer.hasPermission("enderchest.3") && !customPlayer.hasPermission(RankList.MODERATEUR)) {
                setSlotData(ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°" + i, new ItemBuilder().setMaterial(Material.BARRIER).toItemStack(), i - 1, null, null);
            }
            if (i == 4 && !customPlayer.hasPermission("enderchest.4") && !customPlayer.hasPermission(RankList.MODERATEUR)) {
                setSlotData(ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°" + i, new ItemBuilder().setMaterial(Material.BARRIER).toItemStack(), i - 1, null, null);
            }
            if (i == 5 && !customPlayer.hasPermission("enderchest.5") && !customPlayer.hasPermission(RankList.MODERATEUR)) {
                setSlotData(ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°" + i, new ItemBuilder().setMaterial(Material.BARRIER).toItemStack(), i - 1, null, null);
            }
            if (i == 6 && !customPlayer.hasPermission(RankList.MODERATEUR)) {
                setSlotData(ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°" + i, new ItemBuilder().setMaterial(Material.BARRIER).toItemStack(), i - 1, null, null);
            }
            if (i == 7 && !customPlayer.hasPermission(RankList.MODERATEUR)) {
                setSlotData(ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°" + i, new ItemBuilder().setMaterial(Material.BARRIER).toItemStack(), i - 1, null, null);
            }
            if (i == 8 && !customPlayer.hasPermission(RankList.MODERATEUR)) {
                setSlotData(ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°" + i, new ItemBuilder().setMaterial(Material.BARRIER).toItemStack(), i - 1, null, null);
            }
            if (i == 9 && !customPlayer.hasPermission(RankList.MODERATEUR)) {
                setSlotData(ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°" + i, new ItemBuilder().setMaterial(Material.BARRIER).toItemStack(), i - 1, null, null);
            }
        }
        plugin.getServer().getScheduler().runTask(plugin, () -> player.openInventory(inventory));

    }

    @Override
    public void onClick(Player player, ItemStack stack, String action, ClickType clickType) {
        EnderChest enderChest = HeavenFactions.get().getEnderChestManager().get(player);
        switch (action) {
            case "ender1":
                Inventory ender1 = Bukkit.getServer().createInventory(null, 9*3, ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°1");
                try {
                    ender1.setContents(BukkitSerialization.fromBase64(enderChest.getEnder1()).getContents());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.openInventory(ender1);
                break;
            case "ender2":
                Inventory ender2 = Bukkit.getServer().createInventory(null, 9*3, ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°2");
                try {
                    ender2.setContents(BukkitSerialization.fromBase64(enderChest.getEnder2()).getContents());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.openInventory(ender2);
                break;
            case "ender3":
                Inventory ender3 = Bukkit.getServer().createInventory(null, 9*3, ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°3");
                try {
                    ender3.setContents(BukkitSerialization.fromBase64(enderChest.getEnder3()).getContents());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.openInventory(ender3);
                break;
            case "ender4":
                Inventory ender4 = Bukkit.getServer().createInventory(null, 9*3, ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°4");
                try {
                    ender4.setContents(BukkitSerialization.fromBase64(enderChest.getEnder4()).getContents());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.openInventory(ender4);
                break;
            case "ender5":
                Inventory ender5 = Bukkit.getServer().createInventory(null, 9*3, ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°5");
                try {
                    ender5.setContents(BukkitSerialization.fromBase64(enderChest.getEnder5()).getContents());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.openInventory(ender5);
                break;
            case "ender6":
                Inventory ender6 = Bukkit.getServer().createInventory(null, 9*3, ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°6");
                try {
                    ender6.setContents(BukkitSerialization.fromBase64(enderChest.getEnder6()).getContents());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.openInventory(ender6);
                break;
            case "ender7":
                Inventory ender7 = Bukkit.getServer().createInventory(null, 9*3, ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°7");
                try {
                    ender7.setContents(BukkitSerialization.fromBase64(enderChest.getEnder7()).getContents());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.openInventory(ender7);
                break;
            case "ender8":
                Inventory ender8 = Bukkit.getServer().createInventory(null, 9*3, ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°8");
                try {
                    ender8.setContents(BukkitSerialization.fromBase64(enderChest.getEnder8()).getContents());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.openInventory(ender8);
                break;
            case "ender9":
                Inventory ender9 = Bukkit.getServer().createInventory(null, 9*3, ChatColor.LIGHT_PURPLE + "EnderChest de " + player.getName() + " N°9");
                try {
                    ender9.setContents(BukkitSerialization.fromBase64(enderChest.getEnder9()).getContents());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.openInventory(ender9);
                break;
        }
    }

    @Override
    public void onClose(Player player) {
        player.playSound(player.getLocation(), Sound.CHEST_CLOSE, 1.0F, 1.0F);
    }
}
