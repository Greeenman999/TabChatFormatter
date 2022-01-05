package de.greenman999.lpt.util;

import de.greenman999.lpt.LPF;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TabUserCache {

    private final LPF LPF;
    private final LuckPerms api;
    private Map<UUID, LPFUser> users;

    public TabUserCache(LPF LPF) {
        this.LPF = LPF;

        api = LPF.getAPI();
        users = new HashMap<UUID, LPFUser>();
    }



    public void reloadCaches() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            api.getUserManager().loadUser(player.getUniqueId());
        }
        for(User user : api.getUserManager().getLoadedUsers()) {
            Player player = Bukkit.getPlayer(user.getUniqueId());


            String prefix = user.getCachedData().getMetaData().getPrefix();
            String suffix = user.getCachedData().getMetaData().getSuffix();
            String messageColor = user.getCachedData().getMetaData().getMetaValue("messageColor");
            String usernameColor = user.getCachedData().getMetaData().getMetaValue("usernameColor");


            LPFUser userObject = new LPFUser(user.getUniqueId(),prefix,suffix,usernameColor,messageColor);
            if(users.containsKey(user.getUniqueId())) {
                users.remove(user.getUniqueId());
            }
            users.put(user.getUniqueId(),userObject);






        }
    }


    public Map<UUID, LPFUser> getUsers() {
        return users;
    }
}
