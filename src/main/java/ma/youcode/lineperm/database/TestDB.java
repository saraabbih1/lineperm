package ma.youcode.lineperm.database;

import java.util.List;
import ma.youcode.lineperm.dao.FichierDAO;
import ma.youcode.lineperm.model.FichierProtege;

public class TestDB {

    public static void main(String[] args) {

FichierDAO fichierDAO = new FichierDAO();

fichierDAO.updateDroits(2, "r--");

FichierProtege fichier = fichierDAO.findById(2);

System.out.println("Nom : " + fichier.getNom());
System.out.println("Owner Read : " + fichier.isOwnerRead());
System.out.println("Owner Write : " + fichier.isOwnerWrite());
System.out.println("Other Read : " + fichier.isOtherRead());
        
}}