package ma.youcode.lineperm.service;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ma.youcode.lineperm.model.FichierProtege;




public class FileService {
    

private Map<String, FichierProtege> fichiers = new HashMap<>();

public void charger(){
    fichiers.clear();
   Path path = Path.of("src/main/resources/files.txt");

   if(!Files.exists(path)){
    return;
   }
   try {
        List<String> lines = Files.readAllLines(path);
        for(String Line : lines){
            String part[] = Line.split(";" , 2);
           String nom= part[0];
           String proprietaire=part[1];
        
        }
   } catch (Exception e) {
    System.out.println("kyn ghalat ");
  
}
}
}
