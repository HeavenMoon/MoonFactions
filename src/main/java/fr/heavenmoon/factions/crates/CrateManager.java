package fr.heavenmoon.factions.crates;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.utils.Hologram;
import fr.heavenmoon.factions.utils.LocationUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CrateManager {

    private final HeavenFactions plugin;
    private final List<CrateBox> crateBoxs;

    public CrateManager(HeavenFactions plugin) {
        this.plugin = plugin;
        this.crateBoxs = new ArrayList<>();
        loadCrates();
    }

    private void loadCrates() {
        ConfigurationSection crateSection = plugin.getConfig().getConfigurationSection("crates");
        if (crateSection != null) {
            for (String crate : crateSection.getKeys(false)) {
                Location crateLocation = LocationUtils.str2loc(plugin.getConfig().getString("crates." + crate + ".loc"));
                Hologram hologram = new Hologram(crateLocation.clone().add(0.5,0,0.5), "Loading..");
                hologram.setLines(ChatColor.LIGHT_PURPLE + "-=x=x=x=x=x=x=x=x=-", ChatColor.AQUA + "Crate Box", ChatColor.GRAY + "(Clic droit avec une clé)", ChatColor.LIGHT_PURPLE + "-=x=x=x=x=x=x=x=x=-");
                CrateBox box = new CrateBox(crateLocation, hologram);
                crateBoxs.add(box);
                plugin.getServer().getOnlinePlayers().forEach(hologram::displayTo);
            }
        }
    }

    public void loadPlayer(Player player) {
        crateBoxs.forEach(crate -> crate.getHologram().displayTo(player));
    }

    public void unloadCrates() {
        crateBoxs.forEach(crateBox -> {
            plugin.getServer().getOnlinePlayers().forEach(player -> {
                crateBox.getHologram().removeFrom(player);
            });
        });
        crateBoxs.clear();
    }

    public void addCrateBox(Location location, int number) {
        plugin.getConfig().set("crates." + number + ".loc", LocationUtils.loc2str(location));
        plugin.saveConfig();
        unloadCrates();
        loadCrates();
    }

    public void giveKeyForCrate(Player player, CrateUnit crate) {
        player.getInventory().addItem(new CrateKey(crate).toItemStack());
    }

    public boolean containsLocation(Location location) {
        return crateBoxs.stream().anyMatch(cb -> cb.getLocation().equals(location));
    }

    public List<CrateBox> getCrateBoxs() {
        return crateBoxs;
    }
}
