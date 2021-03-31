package xyz.damt.handlers.config;

import lombok.Getter;
import xyz.damt.handlers.config.sections.DatabaseHandler;

public class ConfigHandler {

    @Getter private final DatabaseHandler databaseHandler;

    public ConfigHandler() {
        this.databaseHandler = new DatabaseHandler();
    }


}
