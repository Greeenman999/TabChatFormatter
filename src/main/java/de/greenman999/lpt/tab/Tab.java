package de.greenman999.lpt.tab;

import de.greenman999.lpt.LPF;
import de.greenman999.lpt.util.LPFUser;
import de.greenman999.lpt.util.TabUserCache;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class Tab {

    private final TabUserCache tabUserCache;
    private final LPF LPF;

    private final Map<UUID, LPFUser> users;
    private final FileConfiguration config;
    private String prefixSplitCharacter;
    private String suffixSplitCharacter;

    public Tab(TabUserCache tabUserCache, LPF LPF) {
        this.tabUserCache = tabUserCache;
        this.LPF = LPF;

        users = tabUserCache.getUsers();
        config = LPF.getConfig();
        prefixSplitCharacter = config.getString("prefix-split-character");
        suffixSplitCharacter = config.getString("suffix-split-character");
    }




    public void updateTablist() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            LPFUser user = users.get(player.getUniqueId());
            String prefix = user.prefix();
            String usernameColor = user.usernameColor();
            String suffix = user.suffix();
            String username = LegacyComponentSerializer.legacyAmpersand().serialize(player.displayName());

            String tabFormat = config.getString("tab-format");

            if(prefix == null) {
                prefix = "";
                prefixSplitCharacter = "";
            }
            if(usernameColor == null) {
                usernameColor = "";
            }
            if(suffix == null) {
                suffix = "";
                suffixSplitCharacter = "";
            }

            player.sendMessage("Prefix: " + prefix);
            player.sendMessage("PrefixSplitCharacter: " + prefixSplitCharacter);
            player.sendMessage("Username Color: " + usernameColor);
            player.sendMessage("Username: " + username);
            player.sendMessage("Suffix: " + suffix);
            player.sendMessage("SuffixSplitCharacter: " + suffixSplitCharacter);




            tabFormat = tabFormat
                    .replace("{prefix}",prefix)
                    .replace("{prefixSplit}", prefixSplitCharacter)
                    .replace("{username}",usernameColor + username)
                    .replace("{suffix}", suffix)
                    .replace("{suffixSplit}",suffixSplitCharacter);

            player.playerListName(Component.text(ChatColor.translateAlternateColorCodes('&', tabFormat)));
        }
    }
}
