package ma.youcode.lineperm.model;

public class LogEntry {

    private final String date;
    private final String heure ;
    private final String user;
    private final String action;
    private final String fichier;
    private final String status;

    public LogEntry(String date, String heure, String user, String action,String fichier, String status) {
        this.date = date;
           this.heure = heure;
        this.user = user;
        this.action = action;
          this.fichier = fichier;
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
    public String getHeure() {
    return heure;
}

public String getFichier() {
    return fichier;
}
}