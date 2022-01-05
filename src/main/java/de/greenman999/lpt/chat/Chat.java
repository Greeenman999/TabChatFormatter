package de.greenman999.lpt.chat;


import de.greenman999.lpt.LPF;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class Chat implements Listener {

    private final LPF lpf;

    public Chat(LPF lpf) {
        this.lpf = lpf;

    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        String prefixSplitCharacter = lpf.getConfig().getString("prefix-split-character");
        String suffixSplitCharacter = lpf.getConfig().getString("suffix-split-character");

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        User lpUser = lpf.getAPI().getUserManager().getUser(player.getUniqueId());

        String prefix = lpUser.getCachedData().getMetaData().getPrefix();
        String usernameColor = lpUser.getCachedData().getMetaData().getMetaValue("usernameColor");
        String suffix = lpUser.getCachedData().getMetaData().getSuffix();
        String messageColor = lpUser.getCachedData().getMetaData().getMetaValue("messageColor");
        String username = LegacyComponentSerializer.legacyAmpersand().serialize(player.displayName());
        String message = LegacyComponentSerializer.legacySection().serialize(event.message());

        String chatFormat = lpf.getConfig().getString("chat-format");

        if(prefix == null) {
            prefix = "";
            prefixSplitCharacter = "";
        }
        if(usernameColor == null) {
            usernameColor = "";
        }
        if(suffix == null) {
            suffix = "";
            suffixSplitCharacter = "";
        }
        if(messageColor == null) {
            messageColor = "";
        }




        chatFormat = chatFormat
                .replace("{prefix}",prefix)
                .replace("{prefixSplit}", prefixSplitCharacter)
                .replace("{username}",usernameColor + username)
                .replace("{suffix}", suffix)
                .replace("{suffixSplit}",suffixSplitCharacter)
                .replace("{message}", messageColor + message);


        event.message(Component.text(chatFormat));
    }



}
