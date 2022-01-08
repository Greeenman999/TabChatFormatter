package de.greenman999.tabchatformatter;

import de.greenman999.tabchatformatter.templateprovider.TemplateProvider;
import de.greenman999.tabchatformatter.templateresolver.FormatPreprocessor;
import org.bukkit.Bukkit;

import java.util.HashSet;

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
        /*for(Player player : Bukkit.getOnlinePlayers()) {
            Component tabName = MiniMessage.builder()
                    .placeholderResolver(resolveFor(player, player.displayName()))
                    .build()
                    .parse(tabChatFormatter.getConfig().getString("tab-format"), templatesFor(player, player.displayName()));
            player.playerListName(tabName);
        }*/
    }

    public void startScheduler() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(tabChatFormatter, () -> {
            formatTablistEntries();
        }, 0, 3*20);
    }

}
