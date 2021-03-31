package xyz.damt.team;

import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Atlas;
import xyz.damt.profile.Profile;
import xyz.damt.team.Team;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class TeamHandler {

    private final Atlas atlas;

    public TeamHandler() {
        this.atlas = JavaPlugin.getPlugin(Atlas.class);
    }

    public void loadAllTeams() {
        atlas.getServer().getScheduler().runTaskAsynchronously(atlas, () -> {

            atlas.getTeams().find().forEach((Consumer<? super Document>) document -> {
                new Team(document.getString("_id"), UUID.fromString(document.getString("leader"))
                , document.getList("teamPlayers", String.class));
           });

        });
    }

    public Team getTeamOfPlayer(Player player) {
        return Profile.getProfileByUUID(player.getUniqueId()).getTeam();
    }

}
