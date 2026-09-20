package ma.youcode.lineperm.ui;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.model.LogEntry;
import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.FileService;
import ma.youcode.lineperm.service.LogAnalyzer;
import ma.youcode.lineperm.service.LogService;
import ma.youcode.lineperm.service.UserService;

public class ConsoleApp {


    private final UserService userService;
    private final FileService fileService;
    private final Scanner scanner;
    public boolean run ;
    public User conectUser;
    private  final LogService logService;
    private LogAnalyzer logAnalyzer;

    public ConsoleApp() {

        userService = new UserService();
         fileService = new FileService();
        scanner = new Scanner(System.in);
        logService=new LogService();
    }

    public void demarrer() throws IOException {
        run=true;
             userService.charger();
             fileService.charger();

             List<LogEntry> logs = logService.charger();
             logAnalyzer = new LogAnalyzer(logs);


        System.out.println("LinePermission");

        while(run) { 

            printPrompt();
            
         String comnd=scanner.nextLine().trim().toLowerCase();
   switch(comnd){
     case "singnup":
        signup();
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
    case "ls -l":
                    ls();
                    break;

              
                case "touch":
                    touch();
                    break;

                case "cat":
                    cat();
                    break;

              
                case "nano":
                    nano();
                    break;

                case "chmod":
                    chmod();
                    break;
                 case "stats":
                  stats();

                default:
                    System.out.println("Commande inconnue.");
                    break;
   
}
            }
    // boolean result = userService.createUser("sarra", "1234");

        // System.out.println("create sarra " + result);
    }
    private void stats(){
        System.out.println("nombre total de logs est:" + logAnalyzer.nombreTotalLog());

        System.out.println("utilisateur: ");
        String user =scanner.nextLine();

        System.out.println("nombre de logs pour" + user + " : " + logAnalyzer.nombredeLogUser(user));
    }
    public void signup(){
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


private void ls() {

        if (conectUser == null) {
            System.out.println("Permission denied.");
            return;
        }

        for (FichierProtege fichier : fileService.lister()) {

            String ownerPermissions =
                    permissionOwner(fichier);

            String otherPermissions =
                    permissionOthers(fichier);

            System.out.println(
                    ownerPermissions
                    + "|"
                    + otherPermissions
                    + " "
                    + fichier.getProprietaire()
                    + " "
                    + fichier.getNom()
            );
        }
    }

     private String permissionOwner(FichierProtege fichier) {

        return ""
                + (fichier.isOwnerRead() ? "r" : "-")
                + (fichier.isOwnerWrite() ? "w" : "-")
                + (fichier.isOwnerDelete() ? "d" : "-");
    }

     private String permissionOthers(FichierProtege fichier) {

        return ""
                + (fichier.isOtherRead() ? "r" : "-")
                + (fichier.isOtherWrite() ? "w" : "-")
                + (fichier.isOtherDelete() ? "d" : "-");
    }


     private void touch() {

        if (conectUser == null) {
            System.out.println("Permission denied.");
            return;
        }

        System.out.print("Nom du fichier : ");
        String nom = scanner.nextLine();

        boolean resultat =
                fileService.creer(
                        nom,
                        conectUser.getLogin()
                );

        if (resultat) {
            System.out.println("Fichier cree.");
        } else {
            System.out.println("Impossible de creer le fichier.");
        }
    }

     private void cat() {

        if (conectUser == null) {
            System.out.println("Permission denied.");
            return;
        }

        System.out.print("Nom du fichier : ");
        String nom = scanner.nextLine();

        if (!fileService.existe(nom)) {
            System.out.println("Fichier introuvable.");
            return;
        }

        String contenu =
                fileService.lire(
                        conectUser.getLogin(),
                        nom
                );

        if (contenu == null) {
            System.out.println("Permission denied.");
            return;
        }

        System.out.println(contenu);
    }

 private void nano() {

        if (conectUser == null) {
            System.out.println("Permission denied.");
            return;
        }

        System.out.print("Nom du fichier : ");
        String nom = scanner.nextLine();

        if (!fileService.existe(nom)) {
            System.out.println("Fichier introuvable.");
            return;
        }
        if (!fileService.peutEcrire(
                conectUser.getLogin(),
                nom)) {

            System.out.println("Permission denied.");
            return;
        }

        System.out.println(
                "Ecrivez le contenu. Tapez EOF pour terminer."
        );

        StringBuilder contenu = new StringBuilder();

        while (true) {

            String ligne = scanner.nextLine();

            if (ligne.equals("EOF")) {
                break;
            }

            contenu.append(ligne);
            contenu.append("\n");
        }

        boolean resultat =
                fileService.ecrire(
                        conectUser.getLogin(),
                        nom,
                        contenu.toString()
                );

        if (resultat) {
            System.out.println("Fichier modifie.");
        } else {
            System.out.println("Permission denied.");
        }
    }


  
    private void chmod() {

        if (conectUser == null) {
            System.out.println("Permission denied.");
            return;
        }

        System.out.print(
                "Droit (r/w/d ou -r/-w/-d) : "
        );

        String droitCommande = scanner.nextLine().trim();

        if (droitCommande.length() != 1
        && droitCommande.length() != 2) {
    System.out.println("Commande chmod invalide.");
    return;
}

        boolean ajouter;

        if (droitCommande.charAt(0) == '-') {
            ajouter = false;
        } else {
            ajouter = true;
        }

        char droit;

        if (ajouter) {
            droit = droitCommande.charAt(0);
        } else {
            droit = droitCommande.charAt(1);
        }

        if (droit != 'r'
                && droit != 'w'
                && droit != 'd') {

            System.out.println("Commande chmod invalide.");
            return;
        }

        System.out.print("Nom du fichier : ");
        String nom = scanner.nextLine();

        boolean resultat =
                fileService.chmod(
                        conectUser.getLogin(),
                        droit,
                        ajouter,
                        nom
                );

        if (resultat) {
            System.out.println("Permission modifiee.");
        } else {
            System.out.println("Permission denied.");
        }
    }
    
}

