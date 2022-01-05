package de.greenman999.lpt;

import de.greenman999.lpt.chat.Chat;
import de.greenman999.lpt.listeners.PlayerJoinListener;
import de.greenman999.lpt.tab.Tab;
import de.greenman999.lpt.listeners.PlayerCommandListener;
import de.greenman999.lpt.util.TabUserCache;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandAPIConfig;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class LPF extends JavaPlugin {

    private LPF plugin;
    FileConfiguration config = getConfig();

    PluginManager pluginManager = Bukkit.getPluginManager();

    private final String PREFIX = "§7[§eLPF§7]§r ";
    private Chat chat;
    private TabUserCache tabUserCache;
    private Tab tab;
    private PlayerCommandListener playerCommandListener;
    private PlayerJoinListener playerJoinListener;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        chat = new Chat(this);
        tabUserCache = new TabUserCache(this);
        tab = new Tab(tabUserCache,this);
        playerCommandListener = new PlayerCommandListener(tabUserCache,tab,chat,this);
        playerJoinListener = new PlayerJoinListener(tabUserCache,tab);

        CommandAPI.onEnable(this);
        registerAllCommands();

        pluginManager.registerEvents(playerCommandListener, this);
        pluginManager.registerEvents(playerJoinListener, this);

        saveDefaultConfig();
        this.tabUserCache.reloadCaches();
        minuteScheduler();



        log("§cPlugin successfully enabled and loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log("§cPlugin successfully disabled and unloaded!");
    }

    private void registerAllCommands() {
        registerReloadCommand();
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig().verboseOutput(false).useLatestNMSVersion(true)); //Load with verbose output
    }


    public void log(String string) {
        this.getLogger().info(PREFIX + string);
    }

    public LuckPerms getAPI() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();
            return api;
        }
        return null;
    }

    public void registerReloadCommand() {
        new CommandAPICommand("lpf")
                .withSubcommand(new CommandAPICommand("reload")
                        .withPermission("lpf.reload")
                        .executes((sender,args) -> {
                            this.reloadConfig();
                            this.tabUserCache.reloadCaches();
                            this.tab.updateTablist();
                            sender.sendMessage("§aReloaded LPF!");
                        })

                )
                .withSubcommand(new CommandAPICommand("tablist")
                        .withPermission("lpf.tablist")
                        .executes((sender,args) -> {
                            this.tab.updateTablist();
                            sender.sendMessage("§aUpdated the Tablist!");
                        })
                ).withSubcommand(new CommandAPICommand("test")
                        .withPermission("lpf.test")
                        .executes((sender,args) -> {
                            this.reloadConfig();
                            sender.sendMessage("§aReloaded! " + this.getConfig().getString("prefix-split-character"));
                        })
                ).register();
    }

    public void minuteScheduler() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            this.tabUserCache.reloadCaches();
            this.tab.updateTablist();
        }, 0, 60*20);
    }

}
