package de.greenman999.tabchatformatter.templateprovider;

import de.greenman999.tabchatformatter.TabChatFormatter;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class BasicTemplateProvider extends TemplateProvider {

    public BasicTemplateProvider(TabChatFormatter tabChatFormatter) {
        super(tabChatFormatter);
    }

    @Override
    public HashSet<Template> getTemplates(Player player) {
        HashSet<Template> templates = new HashSet<>();

        templates.add(Template.of("name", player.getName()));
        templates.add(Template.of("uuid", player.getUniqueId().toString()));
        templates.add(Template.of("ping", player.getPing() + ""));
        templates.add(Template.of("experience", player.getTotalExperience() + ""));
        templates.add(Template.of("world", player.getWorld().getName()));
        templates.add(Template.of("level", player.getLevel() + ""));
        templates.add(Template.of("health", player.getHealth() + ""));
        templates.add(Template.of("gamemode", player.getGameMode().name()));

        return templates;
    }

    @Override
    public void init() {

    }
}
