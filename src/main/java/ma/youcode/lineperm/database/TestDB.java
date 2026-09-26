package ma.youcode.lineperm.database;

import ma.youcode.lineperm.dao.FichierDAO;
import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.UserService;

public class TestDB {

    public static void main(String[] args) {

      FichierDAO fichierDAO = new FichierDAO();

FichierProtege fichier = fichierDAO.findById(1);

System.out.println("Fichier trouvé !");
System.out.println("ID : " + fichier.getId());
System.out.println("Nom : " + fichier.getNom());

System.out.println("Owner Read : " + fichier.isOwnerRead());
System.out.println("Owner Write : " + fichier.isOwnerWrite());
System.out.println("Owner Delete : " + fichier.isOwnerDelete());

System.out.println("Other Read : " + fichier.isOtherRead());
System.out.println("Other Write : " + fichier.isOtherWrite());
System.out.println("Other Delete : " + fichier.isOtherDelete());
    }
}