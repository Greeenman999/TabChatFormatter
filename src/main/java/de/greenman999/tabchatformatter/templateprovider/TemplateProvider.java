package de.greenman999.tabchatformatter.templateprovider;

import de.greenman999.tabchatformatter.TabChatFormatter;
import net.kyori.adventure.text.minimessage.Template;
import org.bukkit.entity.Player;

import java.util.Set;

public abstract class TemplateProvider {

    public final TabChatFormatter tabChatFormatter;

    public TemplateProvider(TabChatFormatter tabChatFormatter) {
        this.tabChatFormatter = tabChatFormatter;
    }

    public abstract Set<Template> getTemplates(Player player);

    public abstract void init();

}
