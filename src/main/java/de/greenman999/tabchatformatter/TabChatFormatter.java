package de.greenman999.tabchatformatter;

import de.greenman999.tabchatformatter.templateprovider.BasicTemplateProvider;
import de.greenman999.tabchatformatter.templateprovider.LuckpermsTemplateProvider;
import de.greenman999.tabchatformatter.templateprovider.TemplateProvider;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public final class TabChatFormatter extends JavaPlugin {

    private final PluginManager pluginManager = Bukkit.getPluginManager();

    private final String PREFIX = "§7[§eTabChatFormatter§7]§r ";

    private HashSet<TemplateProvider> provider = new HashSet<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

        CommandAPI.onEnable(this);
        registerAllCommands();
        loadAllProviders();

        ChatListener chatListener = new ChatListener(provider, getConfig().getString("chat-format"));
        pluginManager.registerEvents(chatListener, this);
        saveDefaultConfig();

        log("§cPlugin successfully enabled and loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log("§cPlugin successfully disabled and unloaded!");
    }

    private void registerAllCommands() {

    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig().verboseOutput(false).useLatestNMSVersion(true).silentLogs(true)); //Load with verbose output
    }

    public void log(String string) {
        this.getLogger().info(string);
    }


    private void loadAllProviders() {
        addIfEnabled("LuckPerms", new LuckpermsTemplateProvider(this));

        provider.add(new BasicTemplateProvider(this));
    }

    private void addIfEnabled(String name, TemplateProvider templateProvider) {
        if(pluginManager.isPluginEnabled(name)) {
            provider.add(templateProvider);
            log("Hooked into " + name + "!");
        }
    }


}
