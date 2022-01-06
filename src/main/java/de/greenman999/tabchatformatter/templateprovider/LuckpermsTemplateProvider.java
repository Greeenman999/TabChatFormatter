package de.greenman999.tabchatformatter.templateprovider;

import de.greenman999.tabchatformatter.TabChatFormatter;
import net.kyori.adventure.text.minimessage.Template;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.HashSet;

public class LuckpermsTemplateProvider extends TemplateProvider {

    private LuckPerms api;

    public LuckpermsTemplateProvider(TabChatFormatter tabChatFormatter) {
        super(tabChatFormatter);
    }

    @Override
    public HashSet<Template> getTemplates(Player player) {
        HashSet<Template> templates = new HashSet<>();

        templates.add(Template.of("prefix", api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix()));
        templates.add(Template.of("suffix", api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getSuffix()));
        templates.add(Template.of("usernamecolor", api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getMetaValue("usernamecolor")));
        templates.add(Template.of("messagecolor", api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getMetaValue("messagecolor")));

        return templates;
    }

    @Override
    public void init() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
        }else {
            super.tabChatFormatter.log("An error occurred whilst trying to enable " + this.getClass().getName() + "!");
        }
    }

}
