package ma.youcode.lineperm.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import ma.youcode.lineperm.model.LogEntry;

public class LogAnalyzer {

    private final List<LogEntry> logs;

    public LogAnalyzer(List<LogEntry> logs) {
        this.logs = logs;
    }
    public long nombreTotalLog(){
        return logs.stream().count();
    }

    public long nombredeLogUser(String user){
        return logs .stream().filter(log->log.getUser().equals(user)).count();
    }

    public List<String> getUsers(){
        return logs.stream().map(LogEntry::getUser).distinct().sorted().collect(Collectors.toList());
    }
    public long nombreAccesRefuses(){
        return logs.stream().filter(log->log.getStatus().equals("refused")).count();

    }
    public Map<String,Long> actionParUtilisateur(){
        return logs.stream().collect(Collectors.groupingBy(LogEntry::getUser,Collectors.counting()));
    }

  public Optional<String> utilisateurPlusActif(){
    return logs.stream().collect(Collectors.groupingBy(LogEntry::getUser,
        Collectors.counting()
    )).entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey);
  }

  public Map<String, Long> repartitionActions() {

    return logs.stream()
            .collect(Collectors.groupingBy(
                    LogEntry::getAction,
                    Collectors.counting()
            ));
}

    
}