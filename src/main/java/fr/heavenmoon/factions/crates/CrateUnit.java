package fr.heavenmoon.factions.crates;

import org.bukkit.Material;

import java.util.Arrays;

public enum CrateUnit {

    VOTE(new CrateContainer("Vote", CrateLevel.VOTE, Arrays.asList(
            new CrateItem("Coco", "Test", Material.CHEST, 1, (byte) 0, new String[]{"test3"}, null),
            new CrateItem("Justine", "Test", Material.CHEST, 40, (byte) 0, new String[]{"test2"}, null),
            new CrateItem("Nico", "Test", Material.ENDER_CHEST, 50, (byte) 0, new String[]{"test1"}, null),
            new CrateItem("Hakka", "Test", Material.WATCH, 10, (byte) 0, new String[]{"fezfzefz"}, null),
            new CrateItem("Dayela", "Test", Material.GOLD_SWORD, 30, (byte) 0, new String[]{"fzefzef"}, null),
            new CrateItem("Azbleck", "Test", Material.IRON_CHESTPLATE, 12, (byte) 0, new String[]{"fzefezfz"}, null),
            new CrateItem("Test D'amour", "Test", Material.DIAMOND, 64, (byte) 0, new String[]{"fzefz"}, null))));

    private CrateContainer crate;

    CrateUnit(CrateContainer crate) {
        this.crate = crate;
    }

    public static CrateUnit getCrateByName(String name) {
        return Arrays.stream(values()).filter(crateUnit -> crateUnit.getCrate().getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public CrateContainer getCrate() {
        return crate;
    }

    public void setCrate(CrateContainer crate) {
        this.crate = crate;
    }
}
