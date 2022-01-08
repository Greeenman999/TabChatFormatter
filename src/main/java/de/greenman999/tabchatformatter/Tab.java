package de.greenman999.tabchatformatter;

import de.greenman999.tabchatformatter.templateprovider.TemplateProvider;
import de.greenman999.tabchatformatter.templateresolver.TemplateResolver;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

public class Tab {

    private final TabChatFormatter tabChatFormatter;
    private final HashSet<TemplateProvider> templateProviders;
    private final HashSet<TemplateResolver> templateResolvers;

    public Tab(TabChatFormatter tabChatFormatter) {
        this.tabChatFormatter = tabChatFormatter;
        this.templateProviders = tabChatFormatter.getProviders();
        this.templateResolvers = tabChatFormatter.getResolvers();
    }

    public void formatTablistEntries() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            Component tabName = MiniMessage.builder()
                    .placeholderResolver(resolveFor(player, player.displayName()))
                    .build()
                    .parse(tabChatFormatter.getConfig().getString("tab-format"), templatesFor(player, player.displayName()));
            player.playerListName(tabName);
        }
    }

    private List<Template> templatesFor(@NotNull Player source, @NotNull Component sourceDisplayName) {
        List<Template> templates = new ArrayList<>();
        for (TemplateProvider templateProvider : templateProviders) {
            templates.addAll(templateProvider.getTemplates(source));
        }

        templates.add(Template.of("name", sourceDisplayName));

        return templates;
    }

    private Function<String, ComponentLike> resolveFor(@NotNull Player source, @NotNull Component sourceDisplayName) {
        return (name) -> {
            for (TemplateResolver templateResolver : templateResolvers) {
                return templateResolver.resolve(name, source);
            }
            return null;
        };
    }

    public void startScheduler() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(tabChatFormatter, () -> {
            formatTablistEntries();
        }, 0, 3*20);
    }

}
