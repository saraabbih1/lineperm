package ma.youcode.lineperm.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
   @Override
public FichierProtege findById(int id) {

    String sql = "SELECT * FROM fichiers WHERE id = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();

        if (result.next()) {

            int fichierId = result.getInt("id");
            String nom = result.getString("nom");
            String droits = result.getString("droits");
            int proprietaireId = result.getInt("proprietaire_id");

             System.out.println("ID DB : " + fichierId);
            System.out.println("Nom DB : " + nom);
            System.out.println("Droits DB : " + droits);
          User user = userDAO.findById(proprietaireId);

if (user == null) {
    return null;
}

return new FichierProtege(
    fichierId,
    nom,
    user.getLogin(),
    true,
    true,
    true,
    false,
    false,
    false
);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}
    

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM fichiers WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            int lignesSupprimes = statement.executeUpdate();
            if(lignesSupprimes >0){
                System.out.println("Fichier supprime avec succes");
                        }
                        else{
                            System.out.println("Fichier introuvable");
                        }
        }
        catch(SQLException e){
e.printStackTrace();
        }
    }
}