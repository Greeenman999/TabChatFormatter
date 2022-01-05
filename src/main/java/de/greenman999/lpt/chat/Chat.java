package de.greenman999.lpt.chat;


import de.greenman999.lpt.LPF;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class Chat implements Listener {

    private final LPF LPF;
    private final FileConfiguration config;

    public Chat(LPF LPF) {
        this.LPF = LPF;

        config = LPF.getConfig();
    }

    /*HashMap<UUID, String> prefixes = Util.getPrefixes();
    HashMap<UUID, String> suffixes = Util.getSuffixes();
    HashMap<UUID, String> messageColors = Util.getMessageColors();
    HashMap<UUID, String> usernameColors = Util.getUsernameColors();*/

    /*@EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();

        String prefix = prefixes.get(player.getUniqueId());
        String suffix = suffixes.get(player.getUniqueId());
        String usernameColor = usernameColors.get(player.getUniqueId());
        String messageColor = messageColors.get(player.getUniqueId());
        String username = player.getName();
        String message = LegacyComponentSerializer.legacyAmpersand().serialize(event.message());

        String chatFormat = "";

        if(prefix == null) {
            prefix = "";
        }else {
            prefix = prefix + " ";
        }
        if(suffix == null) {
            suffix = "";
        }else {
            username = username + " ";
        }
        if(usernameColor == null) {
            usernameColor = "";
        }else {
            username = usernameColor + username;
        }
        if(messageColors == null) {
            messageColor == "";
        }

        chatFormat = config.getString("chat-format").replace("{prefix} ",prefix).replace("{suffix}",suffix).replace("{username} ",username).replace("{message}",message);






        event.message(Component.text(ChatColor.translateAlternateColorCodes('&', chatFormat)));
    }*/

}
