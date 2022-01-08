package de.greenman999.tabchatformatter;

import de.greenman999.tabchatformatter.templateprovider.TemplateProvider;
import de.greenman999.tabchatformatter.templateresolver.FormatPreprocessor;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.placeholder.Placeholder;
import net.kyori.adventure.text.minimessage.placeholder.PlaceholderResolver;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class ChatListener implements Listener {
    private final TabChatFormatter tcf;
    private final HashSet<TemplateProvider> templateProviders;
    private final HashSet<FormatPreprocessor> formatPreprocessors;

    public ChatListener(TabChatFormatter tcf) {
        this.tcf = tcf;
        this.templateProviders = tcf.getProviders();
        this.formatPreprocessors = tcf.getResolvers();
    }

    @EventHandler
    public void onChat(AsyncChatEvent asyncChatEvent) {
        asyncChatEvent.renderer((source, sourceDisplayName, message, viewer) -> {
            var messageFormat = tcf.getConfig().getString("chat-format");
            for (FormatPreprocessor preprocessor : formatPreprocessors) {
                messageFormat = preprocessor.resolve(source, messageFormat);
            }

            return MiniMessage.builder()
                    .postProcessor(this::postProcess)
                    .placeholderResolver(templatesFor(source, sourceDisplayName, message, viewer))
                    .build().parse(messageFormat);
        });
    }

    private PlaceholderResolver templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
        List<PlaceholderResolver> templates = new ArrayList<>();
        for (TemplateProvider templateProvider : templateProviders) {
            templates.add(templateProvider.templatesFor(source));
        }

        templates.add(PlaceholderResolver.placeholders(
                Placeholder.component("name", sourceDisplayName),
                Placeholder.component("message", message)
        ));

        return PlaceholderResolver.combining(templates);
    }


    private Component postProcess(Component component){
        return component.replaceText(TextReplacementConfig.builder().match(Pattern.compile(" {2,}")).replacement(" ").build());
    }

}
