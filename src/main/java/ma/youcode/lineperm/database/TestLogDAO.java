package ma.youcode.lineperm.database;

import ma.youcode.lineperm.dao.LogDAO;
import ma.youcode.lineperm.model.LogEntry;

public class TestLogDAO {

    public static void main(String[] args) {

        LogDAO logDAO = new LogDAO();

        LogEntry log = new LogEntry(
            0,
            4,
            2,
            "READ",
            "SUCCESS",
            "2026-09-27 22:00"
        );

        logDAO.save(log);

        System.out.println("Total : " + logDAO.compterTotal());
        System.out.println("Refusés : " + logDAO.compterRefuses());
        System.out.println("Users distincts : " + logDAO.userDistincts());

        System.out.println("Actions par user : " + logDAO.actionsByUser());
        System.out.println("Top fichiers : " + logDAO.topFichiers(3));
        System.out.println("Refusés de sarratest : " + logDAO.refusesByUser("sarratest"));
        System.out.println("User plus actif : " + logDAO.userPlusActif());
        System.out.println("Répartition : " + logDAO.repartitionByAction());
    }
}