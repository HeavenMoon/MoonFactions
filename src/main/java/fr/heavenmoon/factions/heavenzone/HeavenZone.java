package fr.heavenmoon.factions.heavenzone;

import fr.heavenmoon.factions.utils.Cuboid;

public class HeavenZone {

    private Cuboid region;
    private String name;
    private boolean canPvp;

    public HeavenZone(Cuboid region, String name, boolean canPvp) {
        this.region = region;
        this.name = name;
        this.canPvp = canPvp;
    }

    public Cuboid getRegion() {
        return region;
    }

    public void setRegion(Cuboid region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanPvp() {
        return canPvp;
    }

    public void setCanPvp(boolean canPvp) {
        this.canPvp = canPvp;
    }
}
