package de.greenman999.tabchatformatter.templateresolver;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceholderApiResolver implements TemplateResolver{

    @Override
    public @Nullable ComponentLike resolve(@NotNull String name, @NotNull Player player) {
        var replaced = PlaceholderAPI.setPlaceholders(player, name);
        if(!replaced.equals(name)){
            return Component.text(replaced);
        }
        return null;
    }
}
