package ma.youcode.lineperm.dao;
import java.sql.Connection;
import ma.youcode.lineperm.database.*;
public abstract class AbstractDao<T> implements Dao<T> {

    protected Connection connection;

    public AbstractDao() {
        connection = DBConnection.getConnection();
    }
}