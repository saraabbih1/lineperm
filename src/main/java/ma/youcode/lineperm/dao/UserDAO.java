package ma.youcode.lineperm.dao;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ma.youcode.lineperm.model.User;

public class UserDAO extends AbstractDao<User>{
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
    

}