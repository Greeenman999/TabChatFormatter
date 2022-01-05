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

    public Tab(LPF LPF) {
        this.lpf = LPF;

    }



    public void updateTablist() {
        String prefixSplitCharacter = lpf.getConfig().getString("prefix-split-character");
        String suffixSplitCharacter = lpf.getConfig().getString("suffix-split-character");
        for(Player player : Bukkit.getOnlinePlayers()) {
            User lpUser = lpf.getAPI().getUserManager().getUser(player.getUniqueId());
            LPFUser user = new LPFUser(player.getUniqueId(), lpUser.getCachedData().getMetaData().getPrefix(), lpUser.getCachedData().getMetaData().getSuffix(), lpUser.getCachedData().getMetaData().getMetaValue("usernameColor"), lpUser.getCachedData().getMetaData().getMetaValue("messageColor"));
            String prefix = user.prefix();
            String usernameColor = user.usernameColor();
            String suffix = user.suffix();
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
