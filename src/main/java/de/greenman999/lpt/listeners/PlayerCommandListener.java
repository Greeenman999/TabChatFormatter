package de.greenman999.lpt.listeners;

import de.greenman999.lpt.LPF;
import de.greenman999.lpt.chat.Chat;
import de.greenman999.lpt.tab.Tab;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandListener implements Listener {

    private final Tab tab;
    private final Chat chat;
    private final LPF LPF;

    public PlayerCommandListener(Tab tab, Chat chat, LPF LPF) {
        this.tab = tab;
        this.chat = chat;
        this.LPF = LPF;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if(event.getMessage().startsWith("/lp ")) {
            Bukkit.getScheduler().runTaskLater(LPF, () -> {
                this.tab.updateTablist();
            }, 20);
        }
    }
}
