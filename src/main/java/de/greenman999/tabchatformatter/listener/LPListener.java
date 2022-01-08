package de.greenman999.tabchatformatter.listener;

import de.greenman999.tabchatformatter.Tab;
import de.greenman999.tabchatformatter.TabChatFormatter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeMutateEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class LPListener {

    private final Tab tab;
    private final TabChatFormatter tabChatFormatter;
    private final LuckPerms api;
    private final EventBus eventBus;

    public LPListener(TabChatFormatter tabChatFormatter, Tab tab) {
        this.tab = tab;
        this.tabChatFormatter = tabChatFormatter;

        this.api = getAPI();
        this.eventBus = api.getEventBus();

        eventBus.subscribe(tabChatFormatter, NodeMutateEvent.class, this::onNodeMutate);
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

    private void onNodeMutate(NodeMutateEvent event) {
        tab.formatTablistEntries();
    }



}
