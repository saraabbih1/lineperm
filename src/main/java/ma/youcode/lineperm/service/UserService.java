package ma.youcode.lineperm.service;
import java.util.Map;
import java.util.HashMap;
import ma.youcode.lineperm.model.User;

private final Map<String,User> users = new HashMap<>();
private users[] = 0;

public boolean createUser(string login , String passwordHash==null){
    return false;
}
 if(login==null || passwordHash==null){
    return false;
 }

 login=login.trim();
  if(login.isEmpty()|| passwordHash.isEmpty()){
    return false;
  }
  if(login.contain(" ")){
    return false
  }