package fr.heavenmoon.factions.crates;

import fr.heavenmoon.factions.utils.Hologram;
import org.bukkit.Location;

public class CrateBox {

    private Location location;
    private Hologram hologram;

    public CrateBox(Location location, Hologram hologram) {
        this.location = location;
        this.hologram = hologram;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Hologram getHologram() {
        return hologram;
    }

    public void setHologram(Hologram hologram) {
        this.hologram = hologram;
    }
}
