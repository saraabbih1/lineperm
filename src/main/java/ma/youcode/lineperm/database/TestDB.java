package ma.youcode.lineperm.database;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDB {

    public static void main(String[] args) throws Exception {

        Connection connection =
                DriverManager.getConnection("jdbc:sqlite:data/audit.db");

        String sql = Files.readString(
                Path.of("database/schema.sql")
        );

        Statement statement = connection.createStatement();

        for (String request : sql.split(";")) {
            if (!request.trim().isEmpty()) {
                statement.executeUpdate(request);
            }
        }

        ResultSet result = statement.executeQuery(
                "SELECT name FROM sqlite_master WHERE type='table'"
        );

        System.out.println("Tables dans la base :");

        while (result.next()) {
            System.out.println(result.getString("name"));
        }

        connection.close();
    }
}