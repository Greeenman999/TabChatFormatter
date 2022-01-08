package de.greenman999.tabchatformatter.templateresolver;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderApiPreprocessor implements FormatPreprocessor {

    @Override
    public @NotNull String resolve(@NotNull Player player, @NotNull String format) {
        return PlaceholderAPI.setPlaceholders(player, format);
    }

}
