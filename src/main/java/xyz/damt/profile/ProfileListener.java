package xyz.damt.profile;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Atlas;
import xyz.damt.util.CC;

public class ProfileListener implements Listener {

    private final Atlas atlas;

    public ProfileListener() {
        this.atlas = JavaPlugin.getPlugin(Atlas.class);
    }

    @EventHandler
    public void onAsyncJoin(AsyncPlayerPreLoginEvent e) {

        Player player = Bukkit.getPlayer(e.getUniqueId());

        if (player.isOnline() || player != null) {
            player.kickPlayer(CC.translate("&cYou are already online to log in!"));
            return;
        }

        if (!atlas.getProfileHandler().hasProfile(e.getUniqueId())) {
            new Profile(e.getUniqueId()).save();
        }


    }

}
