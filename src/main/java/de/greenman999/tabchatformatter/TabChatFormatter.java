package de.greenman999.tabchatformatter;

import de.greenman999.tabchatformatter.listener.LPListener;
import de.greenman999.tabchatformatter.listener.PlayerJoinListener;
import de.greenman999.tabchatformatter.templateprovider.BasicTemplateProvider;
import de.greenman999.tabchatformatter.templateprovider.LuckpermsTemplateProvider;
import de.greenman999.tabchatformatter.templateprovider.TemplateProvider;
import de.greenman999.tabchatformatter.templateresolver.PlaceholderApiPreprocessor;
import de.greenman999.tabchatformatter.templateresolver.FormatPreprocessor;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandAPIConfig;
import dev.jorel.commandapi.arguments.TextArgument;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public final class TabChatFormatter extends JavaPlugin {

    private final PluginManager pluginManager = Bukkit.getPluginManager();

    private HashSet<TemplateProvider> providers = new HashSet<>();
    private HashSet<FormatPreprocessor> resolvers = new HashSet<>();

    private Tab tab;
    private LPListener lpListener;
    private PlayerJoinListener playerJoinListener;
    private ChatListener chatListener;

    @Override
    public void onEnable() {
        // Plugin startup logic

        CommandAPI.onEnable(this);
        registerAllCommands();
        loadAllProviders();
        loadAllResolvers();

        chatListener = new ChatListener(this);
        tab = new Tab(this);
        lpListener = new LPListener(this, tab);
        playerJoinListener = new PlayerJoinListener(this, tab);
        pluginManager.registerEvents(chatListener, this);
        pluginManager.registerEvents(playerJoinListener, this);
        tab.startScheduler();
        saveDefaultConfig();

        log("§cPlugin successfully enabled and loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log("§cPlugin successfully disabled and unloaded!");
    }

    private void registerAllCommands() {
        registerTabChatFormatterCommand();
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig().verboseOutput(false).useLatestNMSVersion(true).silentLogs(true)); //Load with verbose output
    }

    public void log(String string) {
        this.getLogger().info(string);
    }


    private void loadAllProviders() {
        addTemplateProviderIfEnabled("LuckPerms", new LuckpermsTemplateProvider(this));

        providers.add(new BasicTemplateProvider(this));
    }

    private void loadAllResolvers() {
        addTemplateResolverIfEnabled("PlaceholderAPI",new PlaceholderApiPreprocessor());
    }

    private void addTemplateProviderIfEnabled(String name, TemplateProvider templateProvider) {
        if(pluginManager.isPluginEnabled(name)) {
            providers.add(templateProvider);
            log("Hooked into " + name + "!");
        }
    }

    private void addTemplateResolverIfEnabled(String name, FormatPreprocessor formatPreprocessor) {
        if(pluginManager.isPluginEnabled(name)) {
            resolvers.add(formatPreprocessor);
            log("Hooked into " + name + "!");
        }
    }

    public HashSet<TemplateProvider> getProviders() {
        return providers;
    }

    public HashSet<FormatPreprocessor> getResolvers() {
        return resolvers;
    }

    public void registerTabChatFormatterCommand() {
        new CommandAPICommand("tabchatformatter")
                .withAliases("tcf")
                .withSubcommand(new CommandAPICommand("reload")
                        .withPermission("tabchatformatter.reload")
                        .executes((sender,args) -> {
                            reloadConfig();
                            tab.formatTablistEntries();
                            sender.sendMessage("§aReloaded TabChatFormatter!");
                        })
                )
                .withSubcommand(new CommandAPICommand("test")
                        .withPermission("tabchatformatter.test")
                        .withArguments(new TextArgument("input"))
                        .executes((sender,args) -> {
                        })
                )
                .register();
    }




}
