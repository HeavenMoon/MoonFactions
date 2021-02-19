package fr.heavenmoon.factions.koth;

import fr.heavenmoon.persistanceapi.customs.factions.CustomFaction;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KothFaction
{
	
	private double pourcent = 0.0D;
	private CustomFaction faction;
	private List<Player> kothPlayers = new ArrayList<>();
	
	private boolean inCapture;
	
	public KothFaction(CustomFaction faction)
	{
		this.faction = faction;
	}
	
	public double getPourcent()
	{
		return pourcent;
	}
	
	public void setPourcent(double pourcent)
	{
		this.pourcent = pourcent;
	}
	
	public CustomFaction getFaction()
	{
		return faction;
	}
	
	public void setFaction(CustomFaction faction)
	{
		this.faction = faction;
	}
	
	public List<Player> getKothPlayers()
	{
		return kothPlayers;
	}
	
	public void setKothPlayers(List<Player> kothPlayers)
	{
		this.kothPlayers = kothPlayers;
	}
	
	public boolean isInCapture()
	{
		return inCapture;
	}
	
	public void setInCapture(boolean inCapture)
	{
		this.inCapture = inCapture;
	}
	
	public void capture()
	{
		if (inCapture)
		{
			pourcent = pourcent + 0.2D;
		}
	}
	
	public String pourcentToString()
	{
		StringBuilder sb = new StringBuilder();
		int total = 20;
		for (int i = 0; i < total; i++)
		{
			if (i < total * (pourcent / 100.0))
			{
				sb.append(ChatColor.GREEN + "¦");
			}
			else
			{
				sb.append(ChatColor.GRAY + "¦");
			}
		}
		return sb.toString();
	}
}
