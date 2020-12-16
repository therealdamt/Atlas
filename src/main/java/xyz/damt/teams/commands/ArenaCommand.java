package xyz.damt.teams.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.teams.configuration.Util;
import xyz.damt.teams.managers.ArenaManager;

public class ArenaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("join")) {
                    if (player.hasPermission("arena.join")) {

                        int amount;

                        try {
                            amount = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(Util.chat("&cPlease insert a valid number!"));
                            return false;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            player.sendMessage(Util.chat("&c/arena join (number)"));
                        }


                        ArenaManager.getInstance().tpArena(player);
                        if (!ArenaManager.getInstance().inarena.containsKey(player)) {
                            ArenaManager.getInstance().inarena.put(player, null);
                        } else {
                            player.sendMessage(Util.chat("&cYou are already inside of the arena!"));
                            player.sendMessage(Util.chat("&7&oIf you wish to leave the arena do /arena leave"));
                        }
                    } else {
                        player.sendMessage(Util.chat("&cNo permission"));
                    }
                } else if (args[0].equalsIgnoreCase("leave")) {
                    if (ArenaManager.getInstance().inarena.containsKey(player)) {
                        ArenaManager.getInstance().inarena.remove(player);
                        ArenaManager.getInstance().tpSpawn(player);
                    } else {
                        player.sendMessage(Util.chat("&cYou aren't in an arena!"));
                    }
                } else if (args[0].equalsIgnoreCase("list")) {
                    if (player.hasPermission("arena.list")) {
                        player.sendMessage(Util.chat("&aThere is about " + ArenaManager.getInstance().getArenas() + " arenas!"));
                    }
                }
            } else {
                player.sendMessage(Util.chat("&c/arena <join/leave> <number>"));
            }
        }



        return false;
    }
}
