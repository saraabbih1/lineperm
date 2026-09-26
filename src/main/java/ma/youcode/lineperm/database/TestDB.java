package ma.youcode.lineperm.database;

import ma.youcode.lineperm.dao.FichierDAO;

public class TestDB {

    public static void main(String[] args) {

      FichierDAO fichierDAO = new FichierDAO();

fichierDAO.delete(1);
}}