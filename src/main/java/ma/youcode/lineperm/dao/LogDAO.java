package ma.youcode.lineperm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import ma.youcode.lineperm.model.LogEntry;

public class LogDAO extends AbstractDao<LogEntry> {

    @Override
    public void save(LogEntry log) {

        String sql = """
                INSERT INTO logs (user_id, fichier_id, action, status, date)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, log.getUserId());
            statement.setInt(2, log.getFichierId());
            statement.setString(3, log.getAction());
            statement.setString(4, log.getStatus());
            statement.setString(5, log.getDate());

            statement.executeUpdate();

            System.out.println("Log ajoute avec succs.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override 
    public LogEntry findById(int id){
        String sql = "SELECT * FROM logs WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                int logId=result.getInt("id");
                 int userId = result.getInt("user_id");
                int fichierId = result.getInt("fichier_id");
                String action = result.getString("action");
                String status = result.getString("status");
                String date = result.getString("date");

                return new LogEntry(logId, userId, fichierId, action, status, date);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id){
        System.out.println("les logs ne peuvent pas etre supprime");
    }

    public int compterTotal(){

        String sql = "SELECT COUNT(*) FROM logs";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet result = statement.executeQuery();
            if(result.next()){
                return result.getInt(1);
            }
        }
            catch(SQLException e){
                e.printStackTrace();
            }
        return 0;
    }

    public int compterRefuses(){
        String sql = "SELECT COUNT(*) FROM logs WHERE status =?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,"REFUSED");
           ResultSet result = statement.executeQuery();
            if(result.next()){
                return result.getInt(1);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    
    return 0;}

    public int userDistincts() {

    String sql = "SELECT COUNT(DISTINCT user_id) FROM logs";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {

        ResultSet result = statement.executeQuery();

        if (result.next()) {
            return result.getInt(1);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return 0;
}

public Map<Integer, Integer> actionsByUser() {

    Map<Integer, Integer> resultats = new HashMap<>();

    String sql = "SELECT user_id, COUNT(*) AS total FROM logs GROUP BY user_id ";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            int userId = result.getInt("user_id");
            int total = result.getInt("total");

            resultats.put(userId, total);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return resultats;
}

public Map<String, Integer> topFichiers(int limite) {

    Map<String, Integer> resultats = new HashMap<>();

    String sql = " SELECT f.nom, COUNT(*) AS total FROM logs l JOIN fichiers f ON l.fichier_id = f.id GROUP BY f.id, f.nom ORDER BY total DESC LIMIT ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, limite);

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            String nom = result.getString("nom");
            int total = result.getInt("total");

            resultats.put(nom, total);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return resultats;
}
}