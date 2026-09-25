package ma.youcode.lineperm.dao;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.model.User;

public class FichierDAO extends AbstractDao<FichierProtege>{
    private final UserDAO userDAO;
    public FichierDAO(){
        userDAO = new UserDAO();
    }

    private String construireDroits(FichierProtege fichier ){
        String owner = "";

        owner += fichier.isOwnerRead()?"r" : "-";
        owner += fichier.isOwnerWrite()?"w" : "-";
        owner += fichier.isOwnerDelete()?"d" : "-";

          String other = "";
  other += fichier.isOtherRead() ? "r" : "-";
    other += fichier.isOtherWrite() ? "w" : "-";
    other += fichier.isOtherDelete() ? "d" : "-";

    return owner + "|" + other;
        
    }

    @Override
public void save(FichierProtege fichier) {

    User user = userDAO.findByUsername(fichier.getProprietaire());

    if (user == null) {
        System.out.println("Proprietaire introuvable.");
        return;
    }
    String droits = construireDroits(fichier);

    String sql = "INSERT INTO fichiers (nom, droits, proprietaire_id) VALUES (?, ?, ?)";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, fichier.getNom());
        statement.setString(2, droits);
        statement.setInt(3, user.getId());
        statement.executeUpdate();
        System.out.println("Fichier ajouté avec succès.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}