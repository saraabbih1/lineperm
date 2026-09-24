
package ma.youcode.lineperm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    private DBConnection() {
    }

    public static Connection getConnection() {

        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:data/audit.db");
                System.out.println("Connexion a SQLite reussie !");
            } catch (SQLException e) {
                System.out.println("Erreur de connexion a la base de donnees.");
                e.printStackTrace();
            }
        }

        return connection;
    }
}

