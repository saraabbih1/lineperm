package ma.youcode.lineperm.service;

import org.mindrot.jbcrypt.BCrypt;

import ma.youcode.lineperm.dao.UserDAO;
import ma.youcode.lineperm.model.User;

 public class UserService{
    private final UserDAO userDAO;

    public UserService(){
        userDAO = new UserDAO();

    }
    public boolean createUser(String login,String password){
        if(login == null || password==null){
            return false;
        }

        login=login.trim();
        if(login.isEmpty() || password.isEmpty()){
            return false;
        }

        if(userDAO.findByUsername(login)!=null){
            return false;
        }

        String salt = BCrypt.gensalt();
        String passwordHash=BCrypt.hashpw(password,salt);

        User user = new User(login,passwordHash);

        userDAO.save(user);
        return true;
    }




    public User findUser(String login){
        if(login==null){
            return null;
        }
        return userDAO.findByUsername(login.trim());
    }

     public User authentification(String login,String password){
        if(login==null || password == null){
            return null;
        }
        login=login.trim();
        User user = userDAO.findByUsername(login);
        if(user==null){
            return null;
        }

        boolean passwordCorrect = BCrypt.checkpw(password,user.getPasswordHash());
        if(!passwordCorrect){
            return null;
        }
        return user;
     }


 }