package ma.youcode.lineperm.dao;

public interface Dao<T>{
    void save(T object);
    T findbyId(int id );
    void delete(int id );
}