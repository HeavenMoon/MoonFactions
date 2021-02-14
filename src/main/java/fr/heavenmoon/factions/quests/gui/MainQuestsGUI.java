package fr.heavenmoon.factions.quests.gui;

import fr.moon.core.bukkit.gui.AbstractGui;
import fr.moon.core.common.utils.builders.items.HeadBuilder;
import fr.moon.core.common.utils.builders.items.ItemBuilder;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.quests.QuestType;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.stream.IntStream;

public class MainQuestsGUI extends AbstractGui {

    private final HeavenFactions plugin;
    private final FactionPlayer factionPlayer;

    public MainQuestsGUI(HeavenFactions plugin, FactionPlayer factionPlayer) {
        super(plugin.getApi());
        this.plugin = plugin;
        this.factionPlayer = factionPlayer;
    }

    @Override
    public void display(Player player) {
        this.inventory = plugin.getServer().createInventory(null, 9 * 5, "Quêtes");

        this.setSlotData(ChatColor.GOLD + "Quête Farming", new ItemBuilder(Material.GOLD_HOE).setItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack(), 11, new String[]{ChatColor.GRAY + "Accéder aux quêtes de farming."}, "farming");
        this.setSlotData(ChatColor.GOLD + "Quête Minage", new ItemBuilder(Material.IRON_PICKAXE).setItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack(), 13, new String[]{ChatColor.GRAY + "Accéder aux quêtes de minage."}, "mining");
        this.setSlotData(ChatColor.GOLD + "Quête Killing", new ItemBuilder(Material.DIAMOND_SWORD).setItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack(), 15, new String[]{ChatColor.GRAY + "Accéder aux quêtes de kill."}, "killing");

        IntStream.rangeClosed(27, 35).forEach(slot -> this.setSlotData(ChatColor.BOLD + "Deco", new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), slot, null, null));
        this.setSlotData(ChatColor.GOLD + player.getName(), new HeadBuilder().setOwner(player.getName()).build(), 40, new String[]{
                ChatColor.GRAY + "Level: " + factionPlayer.getQuestLevel().getLevelColor() + factionPlayer.getQuestLevel().getLevelName(),
                ChatColor.GRAY + "Quêtes complétées: " + ChatColor.YELLOW + factionPlayer.getQuestsCompletes().size()
        }, null);
        this.setSlotData(ChatColor.RED + "Fermer l'inventaire", Material.BARRIER, 44, null, "close");

        plugin.getServer().getScheduler().runTask(plugin, () -> player.openInventory(inventory));
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action, ClickType clickType) {
        if (action.equalsIgnoreCase("farming")) {
            plugin.getApi().getGuiManager().openGui(player, new QuestsTemplateGUI(plugin, QuestType.FARMING));
        } else if (action.equalsIgnoreCase("mining")) {
            plugin.getApi().getGuiManager().openGui(player, new QuestsTemplateGUI(plugin, QuestType.MINING));
        } else if (action.equalsIgnoreCase("killing")) {
            plugin.getApi().getGuiManager().openGui(player, new QuestsTemplateGUI(plugin, QuestType.KILLING));
        } else if (action.equalsIgnoreCase("close")) {
            plugin.getApi().getGuiManager().closeGui(player);
        }
    }
}
