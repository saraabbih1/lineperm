package ma.youcode.lineperm.database;

import java.util.List;
import ma.youcode.lineperm.dao.FichierDAO;
import ma.youcode.lineperm.model.FichierProtege;

public class TestDB {

    public static void main(String[] args) {

FichierDAO fichierDAO = new FichierDAO();

FichierProtege fichier =
        new FichierProtege("file1.txt", "sarratest");

fichierDAO.save(fichier);

List<FichierProtege> fichiers =
        fichierDAO.findByProprietaire(4);

System.out.println("Nombre de fichiers : " + fichiers.size());

for (FichierProtege f : fichiers) {
    System.out.println("ID : " + f.getId());
    System.out.println("Nom : " + f.getNom());
    System.out.println("Proprietaire : " + f.getProprietaire());
}
        
}}