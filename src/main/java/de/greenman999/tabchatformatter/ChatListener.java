package de.greenman999.tabchatformatter;

import de.greenman999.tabchatformatter.templateprovider.TemplateProvider;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.Template;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ChatListener implements Listener {

    private final TabChatFormatter tabChatFormatter;

    public ChatListener(TabChatFormatter tabChatFormatter) {
        this.tabChatFormatter = tabChatFormatter;
    }

    @EventHandler
    public void onChat(AsyncChatEvent asyncChatEvent) {
        String chatFormat = tabChatFormatter.getConfig().getString("chat-format");

        if(chatFormat == null) {
            chatFormat = "<prefix> <usernamecolor><name> <suffix> » <message>";
        }
        String finalChatFormat = chatFormat;
        asyncChatEvent.renderer((source, sourceDisplayName, message, viewer) ->
                MiniMessage.builder().build().parse(finalChatFormat, templatesFor(source, sourceDisplayName, message, viewer)));
    }

    private List<Template> templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        List<Template> templates = new ArrayList<>();
        for (TemplateProvider templateProvider : tabChatFormatter.getTemplateProviders()) {
            templates.addAll(templateProvider.getTemplates(source));
        }
        return templates;
    }

}
