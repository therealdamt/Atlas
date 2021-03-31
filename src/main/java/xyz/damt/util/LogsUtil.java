package xyz.damt.util;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class LogsUtil {

    private final ConfigFile configFile;

    public LogsUtil(ConfigFile configFile) {
        this.configFile = configFile;
    }

    @SneakyThrows
    public void addLog(String log) {
        List<String> logs = new ArrayList<>();

        logs.forEach(s -> {
            if (!s.contains(",")) return;

            if (s.contains(log)) {
                String logMessage = s;

                if (logMessage.contains(",")) {
                    String[] args = logMessage.split(",");
                    int number = Integer.parseInt(args[1]);

                    logs.remove(logMessage);
                    logs.add(log + "," + number + 1);
                }
            }
        });

        logs.add(log);
        configFile.getConfig().set("logs", logs);
        configFile.save();
    }

}
