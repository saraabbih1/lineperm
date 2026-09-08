
 package ma.youcode.lineperm.model;
public class User{
    private final String login;
    private final String passwordhash;

    public User(String login,String passwordhash){
      this.login = login ;
      this.passwordhash = passwordhash ;

    }

    public String getLogin(){
        return login;
    }

    public String getPasswordhash(){
        return passwordhash;
    }

}


