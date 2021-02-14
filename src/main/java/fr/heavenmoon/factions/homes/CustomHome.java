package fr.heavenmoon.factions.homes;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class CustomHome {

    private String name;
    private String worldName;
    private double X;
    private double Y;
    private double Z;
    private float Yaw;
    private float Pitch;

    public CustomHome(String name, Location loc) {
        this.name = name;
        this.worldName = loc.getWorld().getName();
        this.X = loc.getX();
        this.Y = loc.getY();
        this.Z = loc.getZ();
        this.Yaw = loc.getYaw();
        this.Pitch = loc.getPitch();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public double getX() {
        return X;
    }

    public void setX(long x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(long y) {
        Y = y;
    }

    public double getZ() {
        return Z;
    }

    public void setZ(long z) {
        Z = z;
    }

    public float getYaw() {
        return Yaw;
    }

    public void setYaw(float yaw) {
        Yaw = yaw;
    }

    public float getPitch() {
        return Pitch;
    }

    public void setPitch(float pitch) {
        Pitch = pitch;
    }

    public Location getLocation() {

        return new Location(Bukkit.getWorld(this.worldName), this.X, this.Y, this.Z, this.Yaw, this.Pitch);
    }
}
