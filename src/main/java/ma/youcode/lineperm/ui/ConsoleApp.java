package ma.youcode.lineperm.ui;
import java.util.Scanner;
import ma.youcode.lineperm.service.UserService;
import ma.youcode.lineperm.service.FileService;

public class ConsoleApp {

    private UserService userService;
     private FileService fileService;
    private Scanner scanner;

    public ConsoleApp() {

        userService = new UserService();
           fileService = new FileService();
        scanner = new Scanner(System.in);
    }

    public void demarrer() {
             userService.charger();
              fileService.charger();

        System.out.println("LinePermission");

        // boolean result = userService.createUser("sarra", "1234");

        // System.out.println("create sarra " + result);
    }
}