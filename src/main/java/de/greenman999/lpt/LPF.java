package de.greenman999.lpt;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LPF extends JavaPlugin {

    private final PluginManager pluginManager = Bukkit.getPluginManager();

    private final String PREFIX = "§7[§eLPF§7]§r ";

    @Override
    public void onEnable() {
        // Plugin startup logic


        CommandAPI.onEnable(this);
        registerAllCommands();


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
        this.getLogger().info(PREFIX + string);
    }

}
