package ma.youcode.lineperm.model;

public class LogEntry {
    private final int id;
    private final int userId;
    private final int fichierId;
    private final String action;
    private final String status;
 private final String date;
    public LogEntry( int id,int userId,int fichierId , String action, String status, String date) {
        
       
    this.id= id;
    this.userId=userId;
    this.fichierId=fichierId;
        this.action = action;
        this.status = status;
        this.date = date;
    }

    public int getId() {
        return this.id;
    }

    public int getUserId() {
        return this.userId;
    }

    public int getFichierId() {
        return this.fichierId;
    }

    public String getAction() {
        return action;
    }

    public String getStatus() {
        return status;
    }
 public String getDate() {
        return date;
    }


}