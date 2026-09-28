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

        String sql = "INSERT INTO logs (user_id, fichier_id, action, status, date) VALUES (?, ?, ?, ?, ?)";

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

public Map<String, Integer> actionsByUser() {

    Map<String, Integer> resultats = new HashMap<>();

String sql = "SELECT u.login, COUNT(*) AS total " +"FROM logs l " + "JOIN users u ON l.user_id = u.id " + "GROUP BY u.id, u.login";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            String login = result.getString("login");
            int total = result.getInt("total");

            resultats.put(login, total);
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

public int refusesByUser(String username) {

    String sql = "SELECT COUNT(*) FROM logs l JOIN users u ON l.user_id = u.id WHERE u.login = ? AND l.status = 'REFUSED'";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, username);

        ResultSet result = statement.executeQuery();

        if (result.next()) {
            return result.getInt(1);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return 0;
}

public String userPlusActif() {

    String sql = " SELECT u.login, COUNT(*) AS total FROM logs l JOIN users u ON l.user_id = u.id GROUP BY u.id, u.login ORDER BY total DESC LIMIT 1";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {

        ResultSet result = statement.executeQuery();

        if (result.next()) {
            return result.getString("login");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}


public Map<String, Integer> repartitionByAction() {

    Map<String, Integer> resultats = new HashMap<>();

    String sql = " SELECT action, COUNT(*) AS total FROM logs GROUP BY action";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            String action = result.getString("action");
            int total = result.getInt("total");

            resultats.put(action, total);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return resultats;
}
}