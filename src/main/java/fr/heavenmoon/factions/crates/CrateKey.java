package fr.heavenmoon.factions.crates;

import fr.heavenmoon.core.common.utils.builders.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CrateKey {

    private String name;
    private CrateUnit crate;

    public CrateKey(CrateUnit crate) {
        this.crate = crate;
        this.name = ChatColor.GRAY + "Clé pour " + crate.getCrate().getLevel().getColorCode() + crate.getCrate().getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CrateUnit getCrate() {
        return crate;
    }

    public void setCrate(CrateUnit crate) {
        this.crate = crate;
    }

    public ItemStack toItemStack() {
        return new ItemBuilder(Material.TRIPWIRE_HOOK).setDisplayName(this.name).setLore(ChatColor.GRAY + "Clic droit sur une box au spawn", ChatColor.GRAY + "pour obtenir des récompenses.").toItemStack();
    }
}
