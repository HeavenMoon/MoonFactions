package fr.heavenmoon.factions.crates;

import fr.moon.core.bukkit.format.Message;
import fr.moon.core.bukkit.gui.AbstractGui;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.utils.FireworkPacket;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Random;

public class CrateGUI extends AbstractGui {

    private final HeavenFactions plugin;
    private CrateUnit crate;
    private CrateItem randomReward;

    boolean gived;
    boolean isDone;

    float soundPower;

    int crateTaskId;
    int times;

    public CrateGUI(HeavenFactions plugin, CrateUnit crate) {
        super(plugin.getApi());
        this.plugin = plugin;
        this.crate = crate;
        this.gived = false;
        this.isDone = false;
        this.times = 0;
        this.soundPower = 0.0F;
    }

    @Override
    public void display(Player player) {
        this.inventory = plugin.getServer().createInventory(null, 9*3, "Crate > " + crate.getCrate().getName());

        plugin.getServer().getScheduler().runTask(plugin, () -> player.openInventory(inventory));

        this.crateTaskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            update(player);
            times++;
            soundPower+=0.02;
        }, 0L, 2L);
    }

    @Override
    public void update(Player player) {
        Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,14,15,16,17,18,19,20,21,22,23,24,25,26)
                .forEach(slot -> this.setSlotData("hey", new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) new Random().nextInt(8)), slot, null, null));
        randomReward = crate.getCrate().getRandomReward();
        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1.0F, this.soundPower);
        if(times >= 50) {
            this.setSlotData(randomReward.getName(), new ItemStack(randomReward.getType(), randomReward.getAmount(), randomReward.getData()), 13, randomReward.getLore(), "give");
            plugin.getServer().getScheduler().cancelTask(this.crateTaskId);
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);

            new FireworkPacket().sendFireworkPacket(player, player.getLocation(), FireworkEffect.builder()
                    .withColor(Color.GREEN)
                    .with(FireworkEffect.Type.STAR)
                    .withFade(Color.PURPLE)
                    .withTrail().build());

            this.isDone = true;
        } else {
            this.setSlotData(randomReward.getName(), new ItemStack(randomReward.getType(), randomReward.getAmount(), randomReward.getData()), 13, randomReward.getLore(), null);
        }
    }

    @Override
    public void onClose(Player player) {
        if(!isDone) {
            plugin.getServer().getScheduler().cancelTask(this.crateTaskId);
            randomReward = crate.getCrate().getRandomReward();
            giveReward(player);
            gived = true;
        } else {
            if(!gived) giveReward(player);
        }
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action, ClickType clickType) {
        if(action.equalsIgnoreCase("give")) {
            giveReward(player);
            gived = true;
            plugin.getApi().getGuiManager().closeGui(player);
        }
    }

    public void giveReward(Player player) {
        if (randomReward.getCommand() != null) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), randomReward.getCommand());
        } else {
            player.getInventory().addItem(randomReward.toItemStack());
        }
        new Message(ChatColor.DARK_PURPLE + "(Box) " + ChatColor.GRAY + "Vous avez remporté " + ChatColor.LIGHT_PURPLE + "x" + randomReward.getAmount() + " " + randomReward.getName()).send(player);
    }
}
