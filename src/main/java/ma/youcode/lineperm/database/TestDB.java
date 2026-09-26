package ma.youcode.lineperm.database;

import ma.youcode.lineperm.dao.FichierDAO;
import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.model.User;
import ma.youcode.lineperm.service.UserService;

public class TestDB {

    public static void main(String[] args) {

      FichierDAO fichierDAO = new FichierDAO();

FichierProtege fichier =
        new FichierProtege("test.txt", "sarratest");

fichierDAO.save(fichier);
    }
}