package ma.youcode.lineperm.service;

import java.util.List;
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
    public long nombreLongParUser(String user){
        return logs.stream().filter(log->log.getUser().equals(user)).count();
    }
    public List<LogEntry> logsParAction(String action){
        return logs.stream().filter(log->log.getAction().equals(action)).collect(Collectors.toList());
    }
}