package ma.youcode.lineperm.service;

import ma.youcode.lineperm.model.User;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    private final Map<String, User> users = new HashMap<>();

    public boolean createUser(String login, String password) {

        if (login == null || password == null) {
            return false;
        }

        login = login.trim();

        if (login.isEmpty() || password.isEmpty()) {
            return false;
        }
          if (users.containsKey(login)) {
            return false;
        }

        String salt = BCrypt.gensalt();

        String passwordhash = BCrypt.hashpw(password, salt);

        User user = new User(login, passwordhash);

        users.put(login, user);
        saveUsers();

       

        return true;
    }
    
       public void charger(){

       try {

        Path path = Path.of("src/main/resources/users.txt");

        if (!Files.exists(path)) {
            return;
        }

        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {

            String[] parts = line.split(":" ,2);

            String login = parts[0];
            String passwordHash = parts[1];

            User user = new User(login, passwordHash);

            users.put(login, user);
        }

    } catch (IOException e) {

        System.out.println("Erreur lors de la lecture du fichier.");
    }


    }

    public User findUser(String login ){
        if(login == null){
            return null;
        }
        return users.get(login);
    }

    public User authentification(String login , String password){
        if(login==null || password==null){
            return null;
        }
         login = login.trim();

         User user=users.get(login);

         if(user==null){
            return null;
         }
boolean passwordCorrct = BCrypt.checkpw(password,user.getPasswordHash());
if(!passwordCorrct){
    return null;
}
return user;

    }

    public void saveUsers() {

    List<String> lines = new ArrayList<>();

    for (User user : users.values()) {

        String line = user.getLogin() + ":" + user.getPasswordHash();

        lines.add(line);
    }

    try {

        Path path = Path.of("src/main/resources/users.txt");

        Files.write(path, lines);

    } catch (IOException e) {

        System.out.println("Erreur lors de la sauvegarde des utilisateurs.");
    }
}

}