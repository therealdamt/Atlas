package xyz.damt.teams;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.teams.commands.ArenaCommand;
import xyz.damt.teams.managers.ArenaManager;
import xyz.damt.teams.managers.TeamGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Team extends JavaPlugin {

    @Getter public static Team instance;

    @Override
    public void onEnable() {
        instance = this;
        ArenaManager.instance = new ArenaManager();
        registerAll();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("arena").setExecutor(new ArenaCommand());
    }
    private void registerListeners() {

    }
    private void loadConfig() {

    }
    private void registerAll() {
        loadConfig();
        registerCommands();
        registerListeners();

        new ArenaManager();
        new TeamGUI();
    }



}
