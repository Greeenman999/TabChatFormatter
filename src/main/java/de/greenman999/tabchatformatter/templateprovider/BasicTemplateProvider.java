package de.greenman999.tabchatformatter.templateprovider;

import de.greenman999.tabchatformatter.TabChatFormatter;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.entity.Player;

import java.util.Set;

public class BasicTemplateProvider extends TemplateProvider {

    public BasicTemplateProvider(TabChatFormatter tabChatFormatter) {
        super(tabChatFormatter);
    }

    @Override
    public Set<Template> getTemplates(Player player) {
        return Set.of(
                Template.of("uuid", player.getUniqueId().toString()),
                Template.of("ping", player.getPing() + ""),
                Template.of("experience", player.getTotalExperience() + ""),
                Template.of("world", player.getWorld().getName()),
                Template.of("level", player.getLevel() + ""),
                Template.of("health", player.getHealth() + ""),
                Template.of("gamemode", player.getGameMode().name())
        );
    }

    @Override
    public void init() {

    }
}
