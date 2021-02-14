package fr.heavenmoon.factions.heavenzone;

import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.utils.Cuboid;
import fr.heavenmoon.factions.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HeavenZoneManager {

    private final HeavenFactions plugin;
    private final List<HeavenZone> heavenZones;

    public HeavenZoneManager(HeavenFactions plugin) {
        this.plugin = plugin;
        this.heavenZones = new ArrayList<>();
        loadHeavenZones();
    }

    public void loadHeavenZones() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("heavenzones");
        if (section != null) {
            for (String heavenZoneName : section.getKeys(false)) {
                Location loc1 = LocationUtils.str2loc(plugin.getConfig().getString("heavenzones." + heavenZoneName + ".loc1"));
                Location loc2 = LocationUtils.str2loc(plugin.getConfig().getString("heavenzones." + heavenZoneName + ".loc2"));
                Cuboid region = new Cuboid(loc1, loc2);
                boolean canPvp = plugin.getConfig().getBoolean("heavenzones." + heavenZoneName + ".pvp");
                HeavenZone heavenZone = new HeavenZone(region, heavenZoneName, canPvp);
                heavenZones.add(heavenZone);
            }
        }
    }

    public boolean containsPlayer(Player player) {
        return heavenZones.stream().anyMatch(heavenZone -> heavenZone.getRegion().isIn(player.getLocation()));
    }

    public void addLocationForHeavenZone(String heavenZoneName, String loc, Location location) {
        plugin.getConfig().set("heavenzones." + heavenZoneName + "." + loc, LocationUtils.loc2str(location));
    }

    public void setPvpForHeavenZone(String heavenZoneName, boolean canPvp) {
        plugin.getConfig().set("heavenzones." + heavenZoneName + ".pvp", canPvp);
    }

}
