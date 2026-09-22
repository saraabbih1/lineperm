package ma.youcode.lineperm;
import java.io.IOException;
import ma.youcode.lineperm.ui.ConsoleApp;

public class Main {

    public static void main(String[] args) throws IOException {
    //     LogService logService = new LogService();

    //     List<LogEntry> logs = logService.charger();

    //     System.out.println(logs.size());
    //    LogAnalyzer analyzer = new LogAnalyzer(logs);

// System.out.println(analyzer.nombreTotalLog());
// System.out.println(analyzer.nombredeLogUser("sarra"));
// System.out.println(analyzer.actionParUtilisateur());

        ConsoleApp app = new ConsoleApp();
        

        app.demarrer();
    }
}
