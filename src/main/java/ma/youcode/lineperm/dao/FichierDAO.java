package ma.youcode.lineperm.dao;
import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.model.User;

public class FichierDAO extends AbstractDao<FichierProtege>{
    private final UserDAO userDAO;
    public FichierDAO(){
        userDAO = new UserDAO();
    }

   
}