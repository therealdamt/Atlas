package xyz.damt.profile;

import com.mongodb.MongoWriteException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Atlas;
import xyz.damt.team.Team;

import java.util.HashMap;
import java.util.UUID;

public class Profile {

    public static HashMap<UUID, Profile> profileHashMap = new HashMap<>();

    private final UUID uuid;
    private Team team;

    private final Atlas atlas;

    public Profile(UUID uuid) {
        this.uuid = uuid;
        profileHashMap.put(uuid, this);

        this.atlas = JavaPlugin.getPlugin(Atlas.class);
        this.load();
    }

    public static Profile getProfileByUUID(UUID uuid) {
        return profileHashMap.get(uuid);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public Team getTeam() {
        return this.team;
    }

    public void save() {
        atlas.getServer().getScheduler().runTaskAsynchronously(atlas, () -> {

            String teamName = team != null ? team.getTeamName() : "null";
            Document userDocument = new Document("_id", uuid.toString()).append("team", teamName);

            try {
                userDocument.append("team", teamName);
                atlas.getMongoExecutor().execute(() -> atlas.getProfiles().insertOne(userDocument));
            } catch (MongoWriteException e) {
                updateDocument(userDocument, "team", teamName);
            }
        });

    }

    public void load() {
        atlas.getServer().getScheduler().runTaskAsynchronously(atlas, () -> {
           Document document = atlas.getProfiles().find(new Document("_id", uuid.toString())).first();
           if (document == null) return;

           Team team = Team.valueOf(document.getString("team"));
           this.team = team;
        });
    }

    public void setTeam(Team team) {
        atlas.getServer().getScheduler().runTaskAsynchronously(atlas, () -> {
           this.team = team;
           updateDocument(
                   atlas.getProfiles().find(new Document("_id", uuid.toString())).first(),
                   "team", team.getTeamName()
           );
        });
    }

    private void updateDocument(Document document, String key, Object value) {
        if (document != null) {
            document.put(key, value);
            atlas.getMongoExecutor().execute(() ->
                    atlas.getTeams().replaceOne(Filters.eq("_id", uuid.toString())
                            , document, new ReplaceOptions().upsert(true)));
        }

    }

}
