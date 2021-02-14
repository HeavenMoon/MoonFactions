package fr.heavenmoon.factions.quests.gui;

import fr.moon.core.bukkit.format.Message;
import fr.moon.core.bukkit.gui.AbstractGui;
import fr.moon.core.common.format.message.PrefixType;
import fr.moon.core.common.utils.builders.items.HeadBuilder;
import fr.moon.core.common.utils.builders.items.ItemBuilder;
import fr.moon.core.common.utils.wrappers.LambdaWrapper;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.quests.AbstractQuest;
import fr.heavenmoon.factions.quests.QuestType;
import fr.heavenmoon.factions.quests.QuestUnit;
import fr.heavenmoon.factions.storage.players.FactionPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.stream.IntStream;

public class QuestsTemplateGUI extends AbstractGui {

    private final HeavenFactions plugin;
    private final QuestType questType;
    private FactionPlayer factionPlayer;

    public QuestsTemplateGUI(HeavenFactions plugin, QuestType questType) {
        super(plugin.getApi());
        this.plugin = plugin;
        this.questType = questType;
    }

    @Override
    public void display(Player player) {
        this.inventory = plugin.getServer().createInventory(null, 9 * 6, "Quêtes de " + questType.name().toLowerCase());

        factionPlayer = plugin.getfPlayersManager().get(player);

        LambdaWrapper<Integer> slot = new LambdaWrapper<>(0);
        Arrays.stream(QuestUnit.values()).filter(quest -> quest.getQuest().getQuestType() == questType).forEach(questUnit -> {
            AbstractQuest quest = questUnit.getQuest();
            ItemBuilder questItem;
            if (factionPlayer.hasCompleteQuest(questUnit)) {
                questItem = new ItemBuilder(quest.getItemDisplayed())
                        .setDisplayName(ChatColor.YELLOW + quest.getDisplayName())
                        .setLore(ChatColor.GRAY + "Type: " + ChatColor.YELLOW + questType.getDisplayName(),
                                ChatColor.GRAY + "Level: " + quest.getQuestLevel().getLevelColor() + quest.getQuestLevel().getLevelName(),
                                ChatColor.GRAY + "Status: " + ChatColor.GREEN + "Accomplie");
                this.setSlotData(questItem.getDisplayName(), questItem.getItemStack(), slot.getData(), questItem.getLore().toArray(new String[0]), null);
            } else if (factionPlayer.getQuestData().getActiveQuest() == questUnit) {
                questItem = new ItemBuilder(quest.getItemDisplayed())
                        .setDisplayName(ChatColor.YELLOW + quest.getDisplayName())
                        .setLore(ChatColor.GRAY + "Type: " + ChatColor.YELLOW + questType.getDisplayName(),
                                ChatColor.GRAY + "Level: " + quest.getQuestLevel().getLevelColor() + quest.getQuestLevel().getLevelName(),
                                ChatColor.GRAY + "Status: " + ChatColor.GOLD + "En cours");
                this.setSlotData(questItem.getDisplayName(), questItem.getItemStack(), slot.getData(), questItem.getLore().toArray(new String[0]), null);
            } else {
                if (!factionPlayer.hasRequiredLevel(questUnit)) {
                    questItem = new ItemBuilder(quest.getItemDisplayed())
                            .setDisplayName(ChatColor.YELLOW + quest.getDisplayName())
                            .setLore(ChatColor.GRAY + "Type: " + ChatColor.YELLOW + questType.getDisplayName(),
                                    ChatColor.GRAY + "Level: " + quest.getQuestLevel().getLevelColor() + quest.getQuestLevel().getLevelName(),
                                    ChatColor.GRAY + "Status: " + ChatColor.RED + "Indisponible",
                                    "",
                                    ChatColor.GRAY + "Afin de pouvoir lancer cette quête,",
                                    ChatColor.GRAY + "veuillez augmenter votre niveau de quête.");
                    this.setSlotData(questItem.getDisplayName(), questItem.getItemStack(), slot.getData(), questItem.getLore().toArray(new String[0]), null);
                } else if(factionPlayer.hasActiveQuest()) {
                    questItem = new ItemBuilder(quest.getItemDisplayed())
                            .setDisplayName(ChatColor.YELLOW + quest.getDisplayName())
                            .setLore(ChatColor.GRAY + "Type: " + ChatColor.YELLOW + questType.getDisplayName(),
                                    ChatColor.GRAY + "Level: " + quest.getQuestLevel().getLevelColor() + quest.getQuestLevel().getLevelName(),
                                    ChatColor.GRAY + "Status: " + ChatColor.RED + "Indisponible",
                                    "",
                                    ChatColor.GRAY + "Afin de pouvoir lancer cette quête,",
                                    ChatColor.GRAY + "veuillez terminer ou annuler",
                                    ChatColor.GRAY + "votre quête en cours.");
                    this.setSlotData(questItem.getDisplayName(), questItem.getItemStack(), slot.getData(), questItem.getLore().toArray(new String[0]), null);
                } else {
                    questItem = new ItemBuilder(quest.getItemDisplayed())
                            .setDisplayName(ChatColor.YELLOW + quest.getDisplayName())
                            .setLore(ChatColor.GRAY + "Type: " + ChatColor.YELLOW + questType.getDisplayName(),
                                    ChatColor.GRAY + "Level: " + quest.getQuestLevel().getLevelColor() + quest.getQuestLevel().getLevelName(),
                                    ChatColor.GRAY + "Status: " + ChatColor.RED + "Disponible");
                    this.setSlotData(questItem.getDisplayName(), questItem.getItemStack(), slot.getData(), questItem.getLore().toArray(new String[0]), "preview\\_//" + quest.getName());
                }
            }
            slot.setData(slot.getData() + 1);
        });

        IntStream.rangeClosed(36, 44).forEach(i -> this.setSlotData(ChatColor.BOLD + "Deco", new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15), i, null, null));
        this.setSlotData(ChatColor.YELLOW + "Revenir en arrière", Material.ARROW, 45, null, "comeback");
        this.setSlotData(ChatColor.GOLD + player.getName(), new HeadBuilder().setOwner(player.getName()).build(), 49, new String[]{
                ChatColor.GRAY + "Level: " + factionPlayer.getQuestLevel().getLevelColor() + factionPlayer.getQuestLevel().getLevelName(),
                ChatColor.GRAY + "Quêtes complétées: " + ChatColor.YELLOW + factionPlayer.getQuestsCompletes().size()
        }, null);
        this.setSlotData(ChatColor.RED + "Fermer l'inventaire", Material.BARRIER, 53, null, "close");

        plugin.getServer().getScheduler().runTask(plugin, () -> player.openInventory(inventory));
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action, ClickType clickType) {
        if (action.equalsIgnoreCase("close")) {
            plugin.getApi().getGuiManager().closeGui(player);
        } else if (action.equalsIgnoreCase("comeback")) {
            plugin.getApi().getGuiManager().openGui(player, new MainQuestsGUI(plugin, factionPlayer));
        } else if (action.equalsIgnoreCase("hasDoQuest")) {
            new Message(PrefixType.ERROR, "Vous avez déjà effectué cette quête.").send(player);
        } else if (action.startsWith("preview")) {
            QuestUnit quest = QuestUnit.getQuestByName(action.split("\\_//")[1]);
            if (quest != null) {
                plugin.getApi().getGuiManager().openGui(player, new StartingQuestGUI(plugin, factionPlayer, quest));
            } else {
                plugin.getApi().getGuiManager().closeGui(player);
                new Message(PrefixType.ERROR, "Une erreur s'est produite. Veuillez contacter un staff.").send(player);
            }
        }
    }
}
