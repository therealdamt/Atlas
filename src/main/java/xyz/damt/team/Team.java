package xyz.damt.team;

import com.mongodb.MongoWriteException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Atlas;
import xyz.damt.profile.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Team {

    public static HashMap<String, Team> teamMap = new HashMap<>();

    private String teamName;
    private UUID leader;
    private List<String> teamPlayers;

    private final Document teamDocument;
    private final Atlas atlas;

    public Team(String teamName, UUID leader, List<String> teamPlayers) {
        this.teamName = teamName;
        this.leader = leader;
        this.teamPlayers = teamPlayers;
        this.atlas = JavaPlugin.getPlugin(Atlas.class);

        teamMap.put(teamName, this);

        this.teamDocument = new Document("_id", teamName).append("leader", leader.toString()).append("teamPlayers", teamPlayers);
    }

    public static Team valueOf(String teamName) {
        return teamMap.get(teamName);
    }

    public String getTeamName() {
        return this.teamName;
    }

    public UUID getLeaderUUID() {
        return this.leader;
    }

    public List<String> getTeamPlayers() {
        return this.teamPlayers;
    }

    public boolean isLeader(UUID uuid) {
        return this.leader == uuid;
    }

    public boolean isInTeam(UUID uuid) {
        return teamPlayers.stream().anyMatch(string -> UUID.fromString(string).equals(uuid));
    }

    public void remove() {
        atlas.getServer().getScheduler().runTaskAsynchronously(atlas, () -> {
            setTeamMembers(null);
           atlas.getMongoExecutor().execute(() -> atlas.getTeams().deleteOne(teamDocument));
        });
    }

    public List<Player> getOnlineTeamPlayers() {
        List<Player> onlinePlayers = new ArrayList<>();
        this.teamPlayers.forEach(string -> { if (atlas.getServer().getPlayer(string) != null) onlinePlayers.add(atlas.getServer().getPlayer(string)); });
        return onlinePlayers;
    }

    public void addPlayer(Player player) {
        atlas.getServer().getScheduler().runTaskAsynchronously(atlas, () -> {
           this.teamPlayers.add(player.getUniqueId().toString());
           Profile.getProfileByUUID(player.getUniqueId()).setTeam(this);

           updateDocument(teamDocument, "teamPlayers", teamPlayers);
        });
    }

    public void setLeader(UUID uuid) {
        atlas.getServer().getScheduler().runTaskAsynchronously(atlas, () -> {
            this.leader = uuid;
            updateDocument(teamDocument, "leader", uuid.toString());
        });
    }

    public void setTeamMembers(List<String> teamMembers) {
        atlas.getServer().getScheduler().runTaskAsynchronously(atlas, () -> {
            this.teamPlayers = teamMembers;
            updateDocument(teamDocument, "teamPlayers", teamPlayers);
        });
    }

    public void removeTeamMember(UUID uuid) {
        atlas.getServer().getScheduler().runTaskAsynchronously(atlas, () -> {
           this.teamPlayers.remove(uuid.toString());
           Profile.getProfileByUUID(uuid).setTeam(null);

           updateDocument(teamDocument,"teamPlayers", teamPlayers);
        });
    }

    public void create() throws MongoWriteException {
        atlas.getServer().getScheduler().runTaskAsynchronously(atlas, () -> {
           atlas.getMongoExecutor().execute(() -> atlas.getTeams().insertOne(teamDocument));
        });
    }

    private void updateDocument(Document document, String key, Object value) {
        if (document != null) {
            document.put(key, value);
            atlas.getMongoExecutor().execute(() ->
                    atlas.getTeams().replaceOne(Filters.eq("_id", teamName)
                            , document, new ReplaceOptions().upsert(true)));
        }

    }


}
