package ma.youcode.lineperm.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ma.youcode.lineperm.model.User;

public class UserDAO extends AbstractDao<User>{
     @Override
    public void save(User user){
        String sql = "INSERT INTO users (login,password) VALUES(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,user.getLogin());
            statement.setString(2,user.getPasswordHash());

            statement.executeUpdate();



        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    @Override
public User findById(int id) {

    String sql = "SELECT * FROM users WHERE id = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();

        if (result.next()) {

            int userId = result.getInt("id");
            String login = result.getString("login");
            String password = result.getString("password");

            return new User(userId, login, password);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}
 @Override
public void delete(int id) {

    String sql = "DELETE FROM users WHERE id = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, id);

        statement.executeUpdate();

        System.out.println("Utilisateur supprime avec succes.");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}