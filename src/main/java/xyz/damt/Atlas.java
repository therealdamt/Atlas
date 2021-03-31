package xyz.damt;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.command.TeamCommand;
import xyz.damt.command.framework.BaseCommand;
import xyz.damt.handlers.ProfileHandler;
import xyz.damt.handlers.TeamHandler;
import xyz.damt.handlers.config.ConfigHandler;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Atlas extends JavaPlugin {

    @Getter private static Atlas instance;

    @Getter private Executor mongoExecutor;

    @Getter private TeamHandler teamHandler;
    @Getter private ConfigHandler configHandler;
    @Getter private ProfileHandler profileHandler;

    @Getter private MongoCollection<Document> profiles;
    @Getter private MongoCollection<Document> teams;

    private MongoDatabase mongoDatabase;
    private MongoClient mongoClient;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.mongoExecutor = Executors.newFixedThreadPool(1);
        this.configHandler = new ConfigHandler();
        this.loadDB();

        this.teamHandler = new TeamHandler();
        this.teamHandler.loadAllTeams();

        this.profileHandler = new ProfileHandler();
        this.profileHandler.loadAllProfiles();

        Arrays.asList(
                new TeamCommand()
        ).forEach(BaseCommand::register);
    }

    private void loadDB() {
        if (getConfigHandler().getDatabaseHandler().isMongoHasAuth()) {
            mongoClient = new MongoClient(
                    new ServerAddress(getConfigHandler().getDatabaseHandler().getMongoHost(), getConfigHandler().getDatabaseHandler().getMongoPort()),
                    MongoCredential.createCredential(
                            getConfigHandler().getDatabaseHandler().getMongoUsername(),
                            getConfigHandler().getDatabaseHandler().getMongoDatabase(),
                            getConfigHandler().getDatabaseHandler().getMongoPassword().toCharArray()
                    ),
                    MongoClientOptions.builder().build()
            );
            mongoClient = new MongoClient(getConfigHandler().getDatabaseHandler().getMongoHost(), getConfigHandler().getDatabaseHandler().getMongoPort());
        } else {
            mongoClient = new MongoClient(getConfigHandler().getDatabaseHandler().getMongoHost(), getConfigHandler().getDatabaseHandler().getMongoPort());
        }
        this.mongoDatabase = mongoClient.getDatabase(getConfigHandler().getDatabaseHandler().getMongoDatabase());

        profiles = mongoDatabase.getCollection("profiles");
         teams = mongoDatabase.getCollection("ranks");

        System.setProperty("DEBUG.GO", "true");
        System.setProperty("DB.TRACE", "true");
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.WARNING);
    }

}
