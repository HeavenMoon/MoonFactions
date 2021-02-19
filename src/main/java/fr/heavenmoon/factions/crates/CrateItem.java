package fr.heavenmoon.factions.crates;

import fr.heavenmoon.core.common.utils.builders.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CrateItem {

    private String name;
    private String rewardName;
    private Material type;
    private int amount;
    private byte data;
    private String[] lore;
    private String command;

    public CrateItem(String name, String rewardName, Material type, int amount, byte data, String[] lore, String command) {
        this.name = name;
        this.rewardName = rewardName;
        this.type = type;
        this.amount = amount;
        this.data = data;
        this.lore = lore;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public Material getType() {
        return type;
    }

    public void setType(Material type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }

    public String[] getLore() {
        return lore;
    }

    public void setLore(String[] lore) {
        this.lore = lore;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public ItemStack toItemStack() {
        return new ItemBuilder(type).setAmount(amount).setData(data).setDisplayName(name).setLore(lore).toItemStack();
    }
}
