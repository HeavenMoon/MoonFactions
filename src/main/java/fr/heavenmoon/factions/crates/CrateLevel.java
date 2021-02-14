package fr.heavenmoon.factions.crates;

import org.bukkit.ChatColor;

public enum CrateLevel {

    VOTE(ChatColor.YELLOW), COMMON(ChatColor.GRAY), RARE(ChatColor.BLUE), EPIC(ChatColor.DARK_PURPLE), LEGENDARY(ChatColor.GOLD);

    private ChatColor colorCode;

    CrateLevel(ChatColor colorCode) {
        this.colorCode = colorCode;
    }

    public ChatColor getColorCode() {
        return colorCode;
    }

    public void setColorCode(ChatColor colorCode) {
        this.colorCode = colorCode;
    }
}
