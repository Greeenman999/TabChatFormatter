package de.greenman999.lpt.tab;

import de.greenman999.lpt.LPF;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Tab {

    private final LPF lpf;

    public Tab(LPF LPF) {
        this.lpf = LPF;

    }



    public void updateTablist() {
        String prefixSplitCharacter = lpf.getConfig().getString("prefix-split-character");
        String suffixSplitCharacter = lpf.getConfig().getString("suffix-split-character");
        for(Player player : Bukkit.getOnlinePlayers()) {
            User lpUser = lpf.getAPI().getUserManager().getUser(player.getUniqueId());

            String prefix = lpUser.getCachedData().getMetaData().getPrefix();
            String usernameColor = lpUser.getCachedData().getMetaData().getMetaValue("usernameColor");
            String suffix = lpUser.getCachedData().getMetaData().getSuffix();
            String username = LegacyComponentSerializer.legacyAmpersand().serialize(player.displayName());

            String tabFormat = lpf.getConfig().getString("tab-format");

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
