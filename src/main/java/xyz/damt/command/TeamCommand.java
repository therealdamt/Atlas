package xyz.damt.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.command.framework.BaseCommand;
import xyz.damt.command.sub.TeamCreateCommand;
import xyz.damt.util.CC;

import java.util.Arrays;
import java.util.List;

public class TeamCommand extends BaseCommand {

    public TeamCommand() {
        super("team", "command.team", "");

        this.playerOnly = true;
        this.getSubCommands().add(new TeamCreateCommand());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length <= 0) {
            sendHelp(player);
        }
     }

    private void sendHelp(Player sender) {

        List<String> strings = Arrays.asList(
                "%bar%",
                "&b&lTeam Commmands Help",
                "%bar%",
                "&7* &c/team create <name> &7- Creates a team for you!",
                "%bar%"
        );

        strings.forEach(string -> sender.sendMessage(CC.translate(string.replace("%bar%", "&7&m------------------------------"))));
    }


}
