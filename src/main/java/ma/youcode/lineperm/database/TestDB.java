package ma.youcode.lineperm.database;

import ma.youcode.lineperm.dao.UserDAO;
import ma.youcode.lineperm.model.User;

public class TestDB {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        User user = userDAO.findByUsername("sara");

        if (user != null) {
            System.out.println("User trouvé !");
            System.out.println("Login : " + user.getLogin());
        } else {
            System.out.println("User introuvable.");
        }
    }
}