package ma.youcode.lineperm.service;

import java.util.List;
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

    
}