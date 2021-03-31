package xyz.damt.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.Atlas;
import xyz.damt.team.Team;
import xyz.damt.util.CC;

public class TeamDeleteCommand extends xyz.damt.command.framework.SubCommand {

    public TeamDeleteCommand() {
        super("delete", "command.teams.delete", "/team delete", "Deletes a team");

        this.playerOnly = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Team team = Atlas.getInstance().getTeamHandler().getTeamOfPlayer(player);

        if (team == null) {
            player.sendMessage(CC.translate("&cYou must be in a team to do this command!"));
            return;
        }

        if (team.getLeaderUUID() != player.getUniqueId()) {
            sender.sendMessage(CC.translate("&cYou must be the leader to this command!"));
            return;
        }

        team.remove();
        player.sendMessage(CC.translate("&aYou have deleted the team " + team.getTeamName()));
    }
}
