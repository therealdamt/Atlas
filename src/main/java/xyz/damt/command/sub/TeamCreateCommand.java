package xyz.damt.command.sub;

import com.mongodb.MongoWriteException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.profile.Profile;
import xyz.damt.team.Team;
import xyz.damt.util.CC;

import java.util.Collections;

public class TeamCreateCommand extends xyz.damt.command.framework.SubCommand {

    public TeamCreateCommand() {
        super("create", "command.team.create", "/team create <name>", "Creates a team for you!");
        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length != 2) {
            player.sendMessage(CC.translate("&c" + getUsage()));
            return;
        }

        Team playerTeam = Profile.getProfileByUUID(player.getUniqueId()).getTeam();

        if (playerTeam != null) {
            player.sendMessage(CC.translate("&cYou are already inside of a team!"));
            return;
        }

        if (args[1].isEmpty()) {
            player.sendMessage(CC.translate("&cPlease input a valid team name!"));
            return;
        }

        String teamName = args[1];

        try {
            new Team(teamName, player.getUniqueId(), Collections.singletonList(player.getUniqueId().toString())).create();
            player.sendMessage(CC.translate("&aYou have succesfuly created a team with the name " + teamName));
        } catch (MongoWriteException e) {
            player.sendMessage(CC.translate("&cThis team already exists!"));
        }
     }
}
