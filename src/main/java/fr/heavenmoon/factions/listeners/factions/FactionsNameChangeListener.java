package fr.heavenmoon.factions.listeners.factions;

import com.massivecraft.factions.event.EventFactionsNameChange;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.storage.factions.CustomFaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionsNameChangeListener implements Listener {

    private final HeavenFactions plugin;

    public FactionsNameChangeListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(EventFactionsNameChange event) {
        CustomFaction customFaction = plugin.getFactionsManager().get(event.getFaction().getId());
        customFaction.setName(event.getNewName());
        plugin.getFactionsManager().update(customFaction);
        plugin.getFactionsManager().commit(customFaction);

    }
}
