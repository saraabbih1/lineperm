package ma.youcode.lineperm.model;

public class LogEntry {

    private final String date;
    private final String user;
    private final String action;
    private final String status;

    public LogEntry(String date, String user, String action, String status) {
        this.date = date;
        this.user = user;
        this.action = action;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getUser() {
        return user;
    }

    public String getAction() {
        return action;
    }

    public String getStatus() {
        return status;
    }
}