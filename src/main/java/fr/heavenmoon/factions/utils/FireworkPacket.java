package fr.heavenmoon.factions.utils;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FireworkPacket {
    private Class<?> packetPlayOutEntityDestroy;
    private Method getPlayerHandle;
    private Method getFireworkHandle;
    private Field getPlayerConnection;
    private Method sendPacket;

    public FireworkPacket() {
        try {
            packetPlayOutEntityDestroy = getMCClass("PacketPlayOutEntityDestroy");
            getPlayerHandle = getCraftClass("entity.CraftPlayer").getMethod("getHandle");
            getFireworkHandle = getCraftClass("entity.CraftFirework").getMethod("getHandle");
            getPlayerConnection = getMCClass("EntityPlayer").getDeclaredField("playerConnection");
            sendPacket = getMCClass("PlayerConnection").getMethod("sendPacket", getMCClass("Packet"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFireworkPacket(Location loc, FireworkEffect fe) {
        try {
            Firework fw = loc.getWorld().spawn(loc, Firework.class);
            FireworkMeta data = (FireworkMeta) fw.getFireworkMeta();
            data.clearEffects();
            data.addEffect(fe);
            data.setPower(0);
            fw.setFireworkMeta(data);
            detonateFirework(fw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFireworkPacket(final Player player, Location loc, FireworkEffect fe) {
        try {
            Firework fw = loc.getWorld().spawn(loc, Firework.class);
            FireworkMeta data = fw.getFireworkMeta();
            data.clearEffects();
            data.addEffect(fe);
            data.setPower(0);
            fw.setFireworkMeta(data);
            Object dpacket = packetPlayOutEntityDestroy.newInstance();
            Field a = packetPlayOutEntityDestroy.getDeclaredField("a");
            a.setAccessible(true);
            a.set(dpacket, new int[]{fw.getEntityId()});
            for (Player pl : fw.getWorld().getPlayers()) {
                if (!pl.equals(player)) sendPacket(pl, dpacket);
            }
            detonateFirework(fw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detonateFirework(Firework fw) {
        try {
            Object nms_firework = getFireworkHandle.invoke(fw);
            Field a = nms_firework.getClass().getDeclaredField("ticksFlown");
            a.setAccessible(true);
            a.set(nms_firework, 2);
            Field b = nms_firework.getClass().getDeclaredField("expectedLifespan");
            b.setAccessible(true);
            b.set(nms_firework, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPacket(final Player player, final Object packet) {
        try {
            sendPacket.invoke(getPlayerConnection.get(getPlayerHandle.invoke(player)), packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Class<?> getMCClass(String name) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String className = "net.minecraft.server." + version + name;
        return Class.forName(className);
    }

    private Class<?> getCraftClass(String name) throws ClassNotFoundException {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
        String className = "org.bukkit.craftbukkit." + version + name;
        return Class.forName(className);
    }

}