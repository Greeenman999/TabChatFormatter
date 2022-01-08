package de.greenman999.tabchatformatter.templateprovider;

import de.greenman999.tabchatformatter.TabChatFormatter;
import net.kyori.adventure.text.minimessage.placeholder.Placeholder;
import net.kyori.adventure.text.minimessage.placeholder.PlaceholderResolver;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record BasicTemplateProvider(TabChatFormatter tabChatFormatter) implements TemplateProvider {

    @Override
    public PlaceholderResolver templatesFor(@NotNull Player player) {
        return PlaceholderResolver.placeholders(
                Placeholder.miniMessage("uuid", player.getUniqueId().toString()),
                Placeholder.miniMessage("ping", player.getPing() + ""),
                Placeholder.miniMessage("experience", player.getTotalExperience() + ""),
                Placeholder.miniMessage("world", player.getWorld().getName()),
                Placeholder.miniMessage("level", player.getLevel() + ""),
                Placeholder.miniMessage("health", player.getHealth() + ""),
                Placeholder.miniMessage("gamemode", player.getGameMode().name())
        );
    }
}
