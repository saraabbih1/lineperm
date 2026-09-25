package ma.youcode.lineperm.database;

import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.UserService;

public class TestDB {

    public static void main(String[] args) {

        UserService userService = new UserService();

        // 1. Test createUser
        boolean created = userService.createUser("sarratest", "1234");

        System.out.println("Create : " + created);

        // 2. Test findUser
        User user = userService.findUser("sarratest");

        if (user != null) {
            System.out.println("Find : User trouve!");
            System.out.println("ID : " + user.getId());
            System.out.println("Login : " + user.getLogin());
        } else {
            System.out.println("Find : User introuvable.");
        }

        // 3. Test authentification
        User authenticated =
                userService.authentification("sarratest", "1234");

        if (authenticated != null) {
            System.out.println("Login : succes !");
        } else {
            System.out.println("Login : echec !");
        }
    }
}