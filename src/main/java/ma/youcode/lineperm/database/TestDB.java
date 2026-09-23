package ma.youcode.lineperm.database;

import java.sql.Connection;

public class TestDB {

    public static void main(String[] args) throws Exception {

        Connection connection = DBConnection.getConnection();

        System.out.println("Connexion SQLite réussie !");

        connection.close();
    }
}