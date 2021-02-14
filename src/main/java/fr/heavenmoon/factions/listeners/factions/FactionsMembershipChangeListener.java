package fr.heavenmoon.factions.listeners.factions;

import com.massivecraft.factions.event.EventFactionsMembershipChange;
import fr.heavenmoon.factions.HeavenFactions;
import fr.heavenmoon.factions.storage.factions.CustomFaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionsMembershipChangeListener implements Listener {

    private final HeavenFactions plugin;

    public FactionsMembershipChangeListener(HeavenFactions plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(EventFactionsMembershipChange event) {
        if (event.getReason() == EventFactionsMembershipChange.MembershipChangeReason.JOIN) {
            CustomFaction customFaction = plugin.getFactionsManager().get(event.getNewFaction().getId());
            customFaction.getPlayers().add(event.getMPlayer().getUuid());
            plugin.getFactionsManager().add(customFaction);
            plugin.getFactionsManager().update(customFaction);
        }
        if (event.getReason() == EventFactionsMembershipChange.MembershipChangeReason.KICK || event.getReason() == EventFactionsMembershipChange.MembershipChangeReason.LEAVE) {
            CustomFaction customFaction = plugin.getFactionsManager().get(event.getNewFaction().getId());
            customFaction.getPlayers().remove(event.getMPlayer().getUuid());
            plugin.getFactionsManager().add(customFaction);
            plugin.getFactionsManager().update(customFaction);
        }
    }
}
