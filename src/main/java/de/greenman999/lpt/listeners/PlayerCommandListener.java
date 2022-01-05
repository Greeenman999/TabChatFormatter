package de.greenman999.lpt.listeners;

import de.greenman999.lpt.LPF;
import de.greenman999.lpt.chat.Chat;
import de.greenman999.lpt.tab.Tab;
import de.greenman999.lpt.util.TabUserCache;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandListener implements Listener {

    private final TabUserCache tabUserCache;
    private final Tab tab;
    private final Chat chat;
    private final LPF LPF;

    public PlayerCommandListener(TabUserCache tabUserCache, Tab tab, Chat chat, LPF LPF) {
        this.tabUserCache = tabUserCache;
        this.tab = tab;
        this.chat = chat;
        this.LPF = LPF;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if(event.getMessage().startsWith("/lp ")) {
            Bukkit.getScheduler().runTaskLater(LPF, () -> {
                this.tabUserCache.reloadCaches();
                this.tab.updateTablist();
            }, 20);
        }
    }
}
