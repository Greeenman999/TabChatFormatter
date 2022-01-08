package de.greenman999.tabchatformatter.templateresolver;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface FormatPreprocessor {
    @NotNull String resolve(@NotNull Player player, @NotNull String format);
}
