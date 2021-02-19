package fr.heavenmoon.factions.enderchest;

import com.google.gson.Gson;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.utils.BukkitSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EnderChestManager
{
	
	private final HeavenFactions plugin;
	
	private final Gson gson = new Gson();
	
	
	public EnderChestManager(HeavenFactions plugin)
	{
		this.plugin = plugin;
	}
	
	public EnderChest get(Player player)
	{
		Path enderChestPath = plugin.getDataFolder().toPath().resolve("EnderChest/" + player.getUniqueId().toString() + ".json");
		EnderChest enderChest;
		try (BufferedReader reader = Files.newBufferedReader(enderChestPath))
		{
			enderChest = gson.fromJson(reader, EnderChest.class);
			return enderChest;
		}
		catch (IOException | NoSuchFieldError e)
		{
			plugin.getLogger().severe("Error while loading ender chest of " + player.getName() + ": " + e);
			return null;
		}
		
	}
	
	public void update(Player player, Inventory inventory)
	{
		EnderChest enderChest = get(player);
		if (inventory.getName().contains("N°1"))
		{
			enderChest.setEnder1(BukkitSerialization.toBase64(inventory));
		}
		if (inventory.getName().contains("N°2"))
		{
			enderChest.setEnder2(BukkitSerialization.toBase64(inventory));
		}
		if (inventory.getName().contains("N°3"))
		{
			enderChest.setEnder3(BukkitSerialization.toBase64(inventory));
		}
		if (inventory.getName().contains("N°4"))
		{
			enderChest.setEnder4(BukkitSerialization.toBase64(inventory));
		}
		if (inventory.getName().contains("N°5"))
		{
			enderChest.setEnder5(BukkitSerialization.toBase64(inventory));
		}
		if (inventory.getName().contains("N°6"))
		{
			enderChest.setEnder6(BukkitSerialization.toBase64(inventory));
		}
		if (inventory.getName().contains("N°7"))
		{
			enderChest.setEnder7(BukkitSerialization.toBase64(inventory));
		}
		if (inventory.getName().contains("N°8"))
		{
			enderChest.setEnder8(BukkitSerialization.toBase64(inventory));
		}
		if (inventory.getName().contains("N°9"))
		{
			enderChest.setEnder9(BukkitSerialization.toBase64(inventory));
		}
		save(enderChest, player);
	}
	
	public void save(EnderChest enderChest, Player player)
	{
		JSONObject json = new JSONObject();
		json.put("ender1", enderChest.getEnder1());
		json.put("ender2", enderChest.getEnder2());
		json.put("ender3", enderChest.getEnder3());
		json.put("ender4", enderChest.getEnder4());
		json.put("ender5", enderChest.getEnder5());
		json.put("ender6", enderChest.getEnder5());
		json.put("ender7", enderChest.getEnder5());
		json.put("ender8", enderChest.getEnder5());
		json.put("ender9", enderChest.getEnder5());
		File file = new File(plugin.getDataFolder() + "/EnderChest/" + player.getUniqueId().toString() + ".json");
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		try (FileWriter fileWriter = new FileWriter(file))
		{
			fileWriter.write(json.toJSONString());
			fileWriter.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
