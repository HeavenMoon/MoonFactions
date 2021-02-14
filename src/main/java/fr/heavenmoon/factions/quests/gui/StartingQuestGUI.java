package fr.heavenmoon.factions.quests.gui;

import fr.moon.core.bukkit.format.Message;
import fr.moon.core.bukkit.gui.AbstractGui;
import fr.moon.core.common.utils.builders.items.HeadBuilder;
import fr.moon.core.common.utils.builders.items.ItemBuilder;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.quests.AbstractQuest;
import fr.heavenmoon.factions.quests.QuestUnit;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.stream.IntStream;

public class StartingQuestGUI extends AbstractGui {

    private final HeavenFactions plugin;
    private final FactionPlayer factionPlayer;
    private final QuestUnit questUnit;
    private final AbstractQuest quest;

    public StartingQuestGUI(HeavenFactions plugin, FactionPlayer factionPlayer, QuestUnit questUnit) {
        super(plugin.getApi());
        this.plugin = plugin;
        this.factionPlayer = factionPlayer;
        this.questUnit = questUnit;
        this.quest = questUnit.getQuest();
    }

    @Override
    public void display(Player player) {
        this.inventory = plugin.getServer().createInventory(null, 9 * 5, "Démarrer la quête ?");

        this.setSlotData(ChatColor.RED + "Décliner", new ItemStack(Material.STAINED_CLAY, 1, (byte) 14), 12, new String[]{ChatColor.GRAY + "Revenir en arrière."}, "comeback");

        ItemBuilder questItem = new ItemBuilder(quest.getItemDisplayed())
                .setDisplayName(ChatColor.YELLOW + quest.getDisplayName())
                .setLore(ChatColor.GRAY + "Type: " + ChatColor.YELLOW + quest.getQuestType().getDisplayName(),
                        ChatColor.GRAY + "Level: " + quest.getQuestLevel().getLevelColor() + quest.getQuestLevel().getLevelName(),
                        ChatColor.GRAY + "Status: " + ChatColor.GREEN + "Disponible");
        this.setSlotData(questItem.getDisplayName(), questItem.getItemStack(), 13, questItem.getLore().toArray(new String[0]), null);

        this.setSlotData(ChatColor.GREEN + "Démarrer", new ItemStack(Material.STAINED_CLAY, 1, (byte) 5), 14, new String[]{ChatColor.GRAY + "Démarrer la quête."}, "start");

        IntStream.rangeClosed(27, 35).forEach(slot -> this.setSlotData(ChatColor.BOLD + "Deco", new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), slot, null, null));
        this.setSlotData(ChatColor.YELLOW + "Revenir en arrière", Material.ARROW, 36, null, "comeback");
        this.setSlotData(ChatColor.GOLD + player.getName(), new HeadBuilder().setOwner(player.getName()).build(), 40, new String[]{
                ChatColor.GRAY + "Level: " + factionPlayer.getQuestLevel().getLevelColor() + factionPlayer.getQuestLevel().getLevelName(),
                ChatColor.GRAY + "Quêtes complétées: " + ChatColor.YELLOW + factionPlayer.getQuestsCompletes().size()
        }, null);
        this.setSlotData(ChatColor.RED + "Fermer l'inventaire", Material.BARRIER, 44, null, "close");

        plugin.getServer().getScheduler().runTask(plugin, () -> player.openInventory(inventory));
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action, ClickType clickType) {
        if (action.equalsIgnoreCase("comeback")) {
            plugin.getApi().getGuiManager().openGui(player, new QuestsTemplateGUI(plugin, quest.getQuestType()));
        } else if (action.equalsIgnoreCase("start")) {
            //TODO : start quest
            plugin.getQuestsManager().startQuest(factionPlayer, this.questUnit);
            plugin.getfPlayersManager().commit(factionPlayer);
            new Message(ChatColor.GREEN + "Vous avez commencé la quête " + ChatColor.YELLOW + quest.getDisplayName()).send(player);
            plugin.getApi().getGuiManager().closeGui(player);
        } else if (action.equalsIgnoreCase("close")) {
            plugin.getApi().getGuiManager().closeGui(player);
        }
    }

}
