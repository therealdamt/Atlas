package xyz.damt.profile;

import com.mongodb.Block;
import org.bson.Document;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Atlas;
import xyz.damt.profile.Profile;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

public class ProfileHandler {

    private final Atlas atlas;

    public ProfileHandler() {
        this.atlas = JavaPlugin.getPlugin(Atlas.class);
    }

    public void loadAllProfiles() {
        atlas.getServer().getScheduler().runTaskAsynchronously(atlas, () -> {
           atlas.getProfiles().find().forEach((Consumer<? super Document>) document -> {
               new Profile(UUID.fromString(document.getString("_id")));
           });
        });
    }

    public Collection<Profile> getAllProfiles() {
        return Profile.profileHashMap.values();
    }

}
