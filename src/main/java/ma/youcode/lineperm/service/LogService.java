package ma.youcode.lineperm.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import ma.youcode.lineperm.model.LogEntry;

public class LogService  {

    private final String filePath = "data/journal.txt";

    public List<LogEntry> charger() throws IOException {

        List<LogEntry> logs = new ArrayList<>();

        List<String> lines = Files.readAllLines(Path.of(filePath));

        for (String line : lines) {

            String[] parts = line.split(";");

            if (parts.length == 6) {

                LogEntry log = new LogEntry(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4],
                        parts[5]
                );

                logs.add(log);
            }
        }

        return logs;
    }
}