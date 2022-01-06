package de.greenman999.tabchatformatter;

import de.greenman999.tabchatformatter.templateprovider.TemplateProvider;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.Template;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;

public class ChatListener implements Listener {

    private final TabChatFormatter tabChatFormatter;

    public ChatListener(TabChatFormatter tabChatFormatter) {
        this.tabChatFormatter = tabChatFormatter;
    }

    @EventHandler
    public void onChat(AsyncChatEvent asyncChatEvent){
        Player player = asyncChatEvent.getPlayer();
        UUID uuid = player.getUniqueId();

        String chatFormat = tabChatFormatter.getConfig().getString("chat-format");

        List<Template> templates = List.of();
        templates.add(Template.of("message", PlainTextComponentSerializer.plainText().serialize(asyncChatEvent.message())));

        for(TemplateProvider templateProvider : tabChatFormatter.getTemplateProviders()) {
            templates.addAll(templateProvider.getTemplates(player));
        }

        asyncChatEvent.renderer((source, sourceDisplayName, message, viewer) -> {
            return MiniMessage.get().parse(chatFormat, templates);

        });
    }

}
