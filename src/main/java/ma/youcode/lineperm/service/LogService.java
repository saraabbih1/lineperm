
package ma.youcode.lineperm.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import ma.youcode.lineperm.model.LogEntry;

public class LogService {

    private final String filePath = "src/main/resources/access.log";

   

    public void enregistrerAction(String user,String action, String fichier,String status) throws IOException {
        LocalDate date = LocalDate.now();

        LocalTime maintenant = LocalTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("HH:mm");

        String heure = maintenant.format(formatter);

        String ligne =
                date + ";"
                + heure + ";"
                + user + ";"
                + action + ";"
                + fichier + ";"
                + status
                + System.lineSeparator();

        Files.writeString(
                Path.of(filePath),
                ligne,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

  

    public List<LogEntry> charger() throws IOException {

        List<LogEntry> logs = new ArrayList<>();

        List<String> lines =
                Files.readAllLines(Path.of(filePath));

        for (String line : lines) {

            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split(";");

            if (parts.length == 6) {

                LogEntry log = new LogEntry(
                        parts[0].trim(), 
                        parts[1].trim(), 
                        parts[2].trim(), 
                        parts[3].trim(),
                        parts[4].trim(), 
                        parts[5].trim()  
                );

                logs.add(log);
            }
        }

        return logs;
    }
}

