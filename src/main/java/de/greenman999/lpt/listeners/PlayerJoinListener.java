package de.greenman999.lpt.listeners;

import de.greenman999.lpt.tab.Tab;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final Tab tab;

    public PlayerJoinListener(Tab tab) {
        this.tab = tab;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.tab.updateTablist();
    }
}
