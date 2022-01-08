package de.greenman999.tabchatformatter.templateprovider;

import de.greenman999.tabchatformatter.TabChatFormatter;
import net.kyori.adventure.text.minimessage.placeholder.Placeholder;
import net.kyori.adventure.text.minimessage.placeholder.PlaceholderResolver;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;

public record LuckpermsTemplateProvider(TabChatFormatter tabChatFormatter) implements TemplateProvider {

    @Override
    public PlaceholderResolver templatesFor(@NotNull Player source) {
        var user = getAPI().getUserManager().getUser(source.getUniqueId());

        if (user != null) {
            var meta = user.getCachedData().getMetaData();
            var templates = new HashSet<Placeholder<?>>();
            templates.add(Placeholder.miniMessage("prefix", Optional.ofNullable(meta.getPrefix()).orElse("")));
            templates.add(Placeholder.miniMessage("suffix", Optional.ofNullable(meta.getSuffix()).orElse("")));
            templates.add(Placeholder.miniMessage("usernamecolor", Optional.ofNullable(meta.getMetaValue("usernamecolor")).orElse("")));
            templates.add(Placeholder.miniMessage("messagecolor", Optional.ofNullable(meta.getMetaValue("messagecolor")).orElse("")));
            return PlaceholderResolver.placeholders(templates);
        }
        return PlaceholderResolver.empty();
    }

    private LuckPerms getAPI() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            return provider.getProvider();
        }else {
            tabChatFormatter.log("An error occurred whilst trying to enable " + this.getClass().getName() + "!");
            return null;
        }
    }

}
