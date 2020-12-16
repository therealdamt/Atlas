package xyz.damt.teams.managers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import xyz.damt.teams.Team;

import java.util.*;
import java.util.concurrent.Callable;

public class ArenaManager {
    @Getter public static ArenaManager instance;
    public HashMap<Player, Integer> inarena = new HashMap<>();
    public int arenas;
    public int playersinarena;


     public void tpSpawn(Player player) {
        World world = Bukkit.getWorld(Team.getInstance().getConfig().getString("Spawn.World"));
        double x = Team.getInstance().getConfig().getInt("Spawn.X");
        double y = Team.getInstance().getConfig().getInt("Spawn.Y");
        double z = Team.getInstance().getConfig().getInt("Spawn.Z");
        float yaw = Team.getInstance().getConfig().getInt("Spawn.Yaw");
        float pitch = Team.getInstance().getConfig().getInt("Spawn.Pitch");

        player.teleport(new Location(world, x, y, z, pitch, yaw));
    }
    public void tpArena(Player player) {

         List<String> arenas = new ArrayList<>(Team.getInstance().getConfig().getConfigurationSection("Arenas").getKeys(false));
        for (String section : arenas) {
            World world = Bukkit.getWorld(Team.getInstance().getConfig().getString("Arenas." + section + ".World"));
            double x = Team.getInstance().getConfig().getInt("Arenas." + section + ".X");
            double y = Team.getInstance().getConfig().getInt("Arenas." + section + ".Y");
            double z = Team.getInstance().getConfig().getInt("Arenas." + section + ".Z");
            float yaw = Team.getInstance().getConfig().getInt("Arenas." + section + ".Yaw");
            float pitch = Team.getInstance().getConfig().getInt("Arenas." + section + ".Pitch");

        }

    }
    public int getArenas() {
         for (String section : Team.getInstance().getConfig().getConfigurationSection("Arenas").getKeys(false)) {
             List<String> number = new ArrayList<>();
             number.add(section);

             arenas = number.size();
         }
         return arenas;
    }
    public int getPlayersInArena(Player player) {
         playersinarena = inarena.values().size();
         return playersinarena;
    }
    public void startArena(Player player) {
         if (getPlayersInArena(player) >= 12) {

         } else {

         }
    }
    public void addToArena(Player player) {
         inarena.put(player, null);
    }
    public void removeFromArena(Player player) {
         inarena.remove(player);
    }
    public boolean isInArena(Player player) {
         return inarena.containsKey(player);
    }

}
