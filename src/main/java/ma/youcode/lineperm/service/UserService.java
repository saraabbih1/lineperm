package ma.youcode.lineperm.service;

import ma.youcode.lineperm.model.User;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
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

        String hash = BCrypt.hashpw(password, salt);

        User user = new User(login, password);

        users.put(login, user);

       

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

            String[] parts = line.split(":");

            String login = parts[0];
            String passwordHash = parts[1];

            User user = new User(login, passwordHash);

            users.put(login, user);
        }

    } catch (IOException e) {

        System.out.println("Erreur lors de la lecture du fichier.");
    }




    }
}