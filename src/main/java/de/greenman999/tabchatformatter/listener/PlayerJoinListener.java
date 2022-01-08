package de.greenman999.tabchatformatter.listener;

import de.greenman999.tabchatformatter.Tab;
import de.greenman999.tabchatformatter.TabChatFormatter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final TabChatFormatter tabChatFormatter;
    private final Tab tab;

    public PlayerJoinListener(TabChatFormatter tabChatFormatter, Tab tab) {
        this.tabChatFormatter = tabChatFormatter;
        this.tab = tab;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        tab.formatTablistEntries();
    }

}
