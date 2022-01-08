package de.greenman999.tabchatformatter.templateprovider;

import net.kyori.adventure.text.minimessage.placeholder.PlaceholderResolver;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface TemplateProvider {

    PlaceholderResolver templatesFor(@NotNull Player source);

}
