package de.greenman999.tabchatformatter;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.UUID;

public class ChatListener implements Listener {

    private final TabChatFormatter tabChatFormatter;

    public ChatListener(TabChatFormatter tabChatFormatter) {
        this.tabChatFormatter = tabChatFormatter;
    }

    public void onChat(AsyncChatEvent asyncChatEvent){
        Player player = asyncChatEvent.getPlayer();
        UUID uuid = player.getUniqueId();

        String chatFormat = tabChatFormatter.getConfig().getString("chat-format");


        asyncChatEvent.renderer((source, sourceDisplayName, message, viewer) -> {
            /*return MiniMessage.builder().placeholderResolver(P.templates(
                    Template.template("prefix", prefix),
                    Template.template("username", sourceDisplayName),
                    Template.template("suffix", suffix),
                    Template.template("message", message)

            )).build().parse(chatFormat);*/

            // Das geht nicht. MiniMessage.builder().templateResolver() gibt es nicht.
            return null;
        });
    }

}
