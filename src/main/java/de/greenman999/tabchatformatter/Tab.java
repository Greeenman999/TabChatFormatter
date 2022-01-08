package de.greenman999.tabchatformatter;

import de.greenman999.tabchatformatter.templateprovider.TemplateProvider;
import de.greenman999.tabchatformatter.templateresolver.FormatPreprocessor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.placeholder.Placeholder;
import net.kyori.adventure.text.minimessage.placeholder.PlaceholderResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

public class Tab {

    private final TabChatFormatter tabChatFormatter;
    private final HashSet<TemplateProvider> templateProviders;
    private final HashSet<FormatPreprocessor> formatPreprocessors;

    public Tab(TabChatFormatter tabChatFormatter) {
        this.tabChatFormatter = tabChatFormatter;
        this.templateProviders = tabChatFormatter.getProviders();
        this.formatPreprocessors = tabChatFormatter.getResolvers();
    }

    public void formatTablistEntries() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            var messageFormat = tabChatFormatter.getConfig().getString("tab-format");
            for (FormatPreprocessor preprocessor : formatPreprocessors) {
                messageFormat = preprocessor.resolve(player, messageFormat);
            }

            var tabName =  MiniMessage.builder()
                    .postProcessor(this::postProcess)
                    .placeholderResolver(templatesFor(player, player.displayName()))
                    .build().parse(messageFormat);
            player.playerListName(tabName);
        }
    }

    private PlaceholderResolver templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName) {
        List<PlaceholderResolver> templates = new ArrayList<>();
        for (TemplateProvider templateProvider : templateProviders) {
            templates.add(templateProvider.templatesFor(source));
        }

        templates.add(PlaceholderResolver.placeholders(
                Placeholder.component("name", sourceDisplayName)
        ));

        return PlaceholderResolver.combining(templates);
    }


    private Component postProcess(Component component){
        return component.replaceText(TextReplacementConfig.builder().match(Pattern.compile(" {2,}")).replacement(" ").build());
    }

    public void startScheduler() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(tabChatFormatter, () -> {
            formatTablistEntries();
        }, 0, 3*20);
    }

}
