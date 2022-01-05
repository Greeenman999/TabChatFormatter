package de.greenman999.lpt.listeners;

import de.greenman999.lpt.tab.Tab;
import de.greenman999.lpt.util.TabUserCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final TabUserCache tabUserCache;
    private final Tab tab;

    public PlayerJoinListener(TabUserCache tabUserCache, Tab tab) {
        this.tabUserCache = tabUserCache;
        this.tab = tab;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.tabUserCache.reloadCaches();
        this.tab.updateTablist();
    }
}
