package ma.youcode.lineperm.ui;
import java.util.Scanner;
import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.UserService;
import ma.youcode.lineperm.service.FileService;


public class ConsoleApp {


    private UserService userService;
    private FileService fileService;
    private Scanner scanner;
    public boolean run ;
    public User conectUser;

    public ConsoleApp() {

        userService = new UserService();
         fileService = new FileService();
        scanner = new Scanner(System.in);
    }

    public void demarrer() {
        run=true;
             userService.charger();
             fileService.charger();


        System.out.println("LinePermission");

        while(run) { 

            printPrompt();
            
         String comnd=scanner.nextLine().trim().toLowerCase();
switch(comnd){
     case "singnup":
        singnup();
        break;
        case "login":
            login();

            break;

            case "logout":
        Logout();
        break;

    case "exit":
        run=false;
        System.out.println("okeee by arras");
        break;

}
            }
    // boolean result = userService.createUser("sarra", "1234");

        // System.out.println("create sarra " + result);
    }
    public void singnup(){
        if(conectUser!=null){
            System.out.println("deja il ya un utilisateur conecter ");
            return;
        }
        System.out.println("login:");
        String login= scanner.nextLine();

        System.out.println("password:");
        String password = scanner.nextLine();

        boolean newuser = userService.createUser(login, password);
        if(newuser){
            System.out.println("creat correct ");
        }
        else{
            System.out.println("noooo");
        }
    }

 public void login(){

    if (conectUser != null) {
        System.out.println(
                "Une session est deja ouverte"
        );
        return;
    }

    System.out.print("Login : ");
    String login = scanner.nextLine();

    System.out.print("password: ");
    String password = scanner.nextLine();

    User user =
            userService.authentification(login, password);

       

    if (user == null) {
        System.out.println("identifiants incorrects.");
        return;
    }  
     conectUser=user;
 }
 private void printPrompt() {

    if (conectUser == null) {
        System.out.print("linperm> ");
    } else {
        System.out.print(
                conectUser.getLogin() + "@linperm> "
        );
    }
}

private void Logout() {

    if (conectUser == null) {
        System.out.println(
                "Aucun utilisateur connecte"
        );
        return;
    }

    conectUser = null;

    System.out.println("Deconnexion reussie."); 
}
}

