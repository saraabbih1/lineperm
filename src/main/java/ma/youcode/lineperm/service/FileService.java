package ma.youcode.lineperm.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.youcode.lineperm.access.ControleAcces;
import ma.youcode.lineperm.model.FichierProtege;




public class FileService {
    

private Map<String, FichierProtege> fichiers = new HashMap<>();
private final Path fichiersPath = Path.of("src/main/resources/files.txt");
Path path = Path.of("data");
public void charger(){
    fichiers.clear();


   if(!Files.exists(path)){
    return;
   }
   try {
        List<String> lines = Files.readAllLines(path);
        for(String Line : lines){
            String parts[] = Line.split(";" , 2);
           String nom= parts[0];
           String proprietaire=parts[1];

            boolean ownerRead = true;
                boolean ownerWrite = true;
                boolean ownerDelete = true;

                boolean otherRead = false;
                boolean otherWrite = false;
                boolean otherDelete = false;

        if(parts.length >= 4){
            String ownerPermissions = parts[2];
            String otherPermissions = parts[3];

            ownerRead = ownerPermissions.charAt(0) == 'r';
            ownerWrite = ownerPermissions.charAt(1) == 'w';
             ownerDelete = ownerPermissions.charAt(2) == 'd';

                    otherRead = otherPermissions.charAt(0) == 'r';
                    otherWrite = otherPermissions.charAt(1) == 'w';
                    otherDelete = otherPermissions.charAt(2) == 'd';

        }
        FichierProtege fichier = new FichierProtege(nom, proprietaire,ownerRead,
                        ownerWrite,
                        ownerDelete,
                        otherRead,
                        otherWrite,
                        otherDelete);
                        fichiers.put(nom,fichier);
        
        }
   } catch (Exception e) {
    System.out.println("kyn ghalat ");
   }
}
    public List<FichierProtege> lister(){
        return new ArrayList<>(fichiers.values());
    }
    private boolean  nomValide(String nom){
        if (nom == null || nom.isBlank()){
            return false;
        }
        return !nom.contains("/")&&!nom.contains("\\")&&!nom.contains(";");
    }




    // creation de fichier 

     public boolean creer(String nom, String proprietaire) {

        if (!nomValide(nom)) {
            return false;
        }

        if (fichiers.containsKey(nom)) {
            return false;
        }

        FichierProtege fichier =
                new FichierProtege(nom, proprietaire);

        try {

            Files.createDirectories(dataPath);

            Path contenuPath = dataPath.resolve(nom);

            Files.writeString(contenuPath, "");

            fichiers.put(nom, fichier);

            sauvegarder();

            return true;

        } catch (IOException e) {

            return false;
        }
    }


    //existe 

     public boolean existe(String nom) {

        return fichiers.containsKey(nom);
    }


    //cat
      public String lire(String login, String nom) {

        FichierProtege fichier = fichiers.get(nom);

        if (fichier == null) {
            return null;
        }

        if (!ControleAcces.estAutorise(login, fichier, 'r')) {
            return null;
        }

        try {

            Path contenuPath = dataPath.resolve(nom);

            if (!Files.exists(contenuPath)) {
                return "";
            }

            return Files.readString(contenuPath);

        } catch (IOException e) {

            return null;
        }
    }

    // nano 

    public boolean ecrire(
            String login,
            String nom,
            String contenu) {

        FichierProtege fichier = fichiers.get(nom);

        if (fichier == null) {
            return false;
        }

        if (!ControleAcces.estAutorise(login, fichier, 'w')) {
            return false;
        }

        try {

            Files.createDirectories(dataPath);

            Path contenuPath = dataPath.resolve(nom);

            Files.writeString(contenuPath, contenu);

            sauvegarder();

            return true;

        } catch (IOException e) {

            return false;
        }
    }

}