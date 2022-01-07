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

import java.util.*;

public class ChatListener implements Listener {
    private final TabChatFormatter tcf;
    private final HashSet<TemplateProvider> templateProviders;
    private final String chatFormat;

    public ChatListener(TabChatFormatter tcf) {
        this.tcf = tcf;
        this.chatFormat = tcf.getConfig().getString("chat-format");
        this.templateProviders = tcf.getProviders();
    }

    @EventHandler
    public void onChat(AsyncChatEvent asyncChatEvent) {
        asyncChatEvent.renderer((source, sourceDisplayName, message, viewer) -> MiniMessage.builder().build().parse(chatFormat, templatesFor(source, sourceDisplayName, message, viewer)));
    }

    private List<Template> templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        List<Template> templates = new ArrayList<>();
        for (TemplateProvider templateProvider : templateProviders) {
            templates.addAll(templateProvider.getTemplates(source));
        }

        templates.add(Template.of("name", sourceDisplayName));
        templates.add(Template.of("message", message));

        return templates;
    }

}
