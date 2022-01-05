package de.greenman999.lpt.tab;

import de.greenman999.lpt.LPF;
import de.greenman999.lpt.util.LPFUser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Tab {

    private final LPF lpf;

    private final FileConfiguration config;
    private String prefixSplitCharacter;
    private String suffixSplitCharacter;

    public Tab(LPF LPF) {
        this.lpf = LPF;

        config = LPF.getConfig();
        prefixSplitCharacter = config.getString("prefix-split-character");
        suffixSplitCharacter = config.getString("suffix-split-character");
    }



    public void updateTablist() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            User lpUser = lpf.getAPI().getUserManager().getUser(player.getUniqueId());
            LPFUser user = new LPFUser(player.getUniqueId(), lpUser.getCachedData().getMetaData().getPrefix(), lpUser.getCachedData().getMetaData().getSuffix(), lpUser.getCachedData().getMetaData().getMetaValue("usernameColor"), lpUser.getCachedData().getMetaData().getMetaValue("messageColor"));
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
