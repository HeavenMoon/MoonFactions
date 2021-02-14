package fr.heavenmoon.factions.utils;

import org.bukkit.Effect;
import org.bukkit.Location;

import java.util.Collection;
import java.util.Random;

public class SmokeUtils {
    public static Random random = new Random();

    // -------------------------------------------- //
    // Spawn once
    // -------------------------------------------- //

    // Single ========
    public static void spawnSingle(Location location, int direction) {
        if (location == null) return;
        location.getWorld().playEffect(location, Effect.SMOKE, direction);
    }

    public static void spawnSingle(Location location) {
        spawnSingle(location, 4);
    }

    public static void spawnSingleRandom(Location location) {
        spawnSingle(location, random.nextInt(9));
    }

    // Simple Cloud ========
    public static void spawnCloudSimple(Location location) {
        for (int i = 0; i <= 8; i++) {
            spawnSingle(location, i);
        }
    }

    public static void spawnCloudSimple(Collection<Location> locations) {
        for (Location location : locations) {
            spawnCloudSimple(location);
        }
    }

    // Random Cloud ========
    public static void spawnCloudRandom(Location location, float thickness) {
        int singles = (int) Math.floor(thickness * 9);
        for (int i = 0; i < singles; i++) {
            spawnSingleRandom(location);
        }
    }

    public static void spawnCloudRandom(Collection<Location> locations, float thickness) {
        for (Location location : locations) {
            spawnCloudRandom(location, thickness);
        }
    }

}