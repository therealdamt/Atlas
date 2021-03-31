package xyz.damt.handlers.config.sections;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.Atlas;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHandler {

    private final FileConfiguration arkRanks;

    @Getter
    private final boolean mongoHasAuth;
    @Getter private final String mongoHost;
    @Getter private final int mongoPort;

    @Getter private final String mongoDatabase;
    @Getter private final String mongoUsername;
    @Getter private final String mongoPassword;

    public DatabaseHandler() {

        this.arkRanks = JavaPlugin.getPlugin(Atlas.class).getConfig();

        this.mongoHasAuth = arkRanks.getBoolean("mongo.auth.enabled");
        this.mongoHost = arkRanks.getString("mongo.host");
        this.mongoPort = arkRanks.getInt("mongo.port");
        this.mongoDatabase = arkRanks.getString("mongo.database");

        this.mongoUsername = arkRanks.getString("mongo.auth.username");
        this.mongoPassword = arkRanks.getString("mongo.auth.password");
    }




}
