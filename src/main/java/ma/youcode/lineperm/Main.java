package ma.youcode.lineperm;
import java.io.IOException;
import java.util.List;
import ma.youcode.lineperm.model.LogEntry;
import ma.youcode.lineperm.service.LogService;
import ma.youcode.lineperm.ui.ConsoleApp;

public class Main {

    public static void main(String[] args) throws IOException {
        LogService logService = new LogService();

        List<LogEntry> logs = logService.charger();

        System.out.println(logs.size());

        ConsoleApp app = new ConsoleApp();
        

        app.demarrer();
    }
}
