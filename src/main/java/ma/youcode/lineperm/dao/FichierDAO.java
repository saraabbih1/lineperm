package ma.youcode.lineperm.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.model.User;

public class FichierDAO extends AbstractDao<FichierProtege> {

    private final UserDAO userDAO;

    public FichierDAO() {
        userDAO = new UserDAO();
    }

    private String construireDroits(FichierProtege fichier) {

        String owner = ""
                + (fichier.isOwnerRead() ? "r" : "-")
                + (fichier.isOwnerWrite() ? "w" : "-")
                + (fichier.isOwnerDelete() ? "d" : "-");

        String other = ""
                + (fichier.isOtherRead() ? "r" : "-")
                + (fichier.isOtherWrite() ? "w" : "-")
                + (fichier.isOtherDelete() ? "d" : "-");

        return owner + "|" + other;
    }

    private FichierProtege convertirResultat(ResultSet result)
            throws SQLException {

        int id = result.getInt("id");
        String nom = result.getString("nom");
        String droits = result.getString("droits");
        String proprietaire = result.getString("login");

        String[] parties = droits.split("\\|");

        String owner = parties[0];
        String other = parties[1];

        boolean ownerRead = owner.charAt(0) != '-';
        boolean ownerWrite = owner.charAt(1) != '-';
        boolean ownerDelete = owner.charAt(2) != '-';

        boolean otherRead = other.charAt(0) != '-';
        boolean otherWrite = other.charAt(1) != '-';
        boolean otherDelete = other.charAt(2) != '-';

        return new FichierProtege(
                id,
                nom,
                proprietaire,
                ownerRead,
                ownerWrite,
                ownerDelete,
                otherRead,
                otherWrite,
                otherDelete
        );
    }

    @Override
    public void save(FichierProtege fichier) {

        User user =
                userDAO.findByUsername(fichier.getProprietaire());

        if (user == null) {
            System.out.println("Proprietaire introuvable.");
            return;
        }

        String droits = construireDroits(fichier);

        String sql =
                "INSERT INTO fichiers " +
                "(nom, droits, proprietaire_id) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, fichier.getNom());
            statement.setString(2, droits);
            statement.setInt(3, user.getId());

            statement.executeUpdate();

            System.out.println(
                    "Fichier ajouté avec succès."
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FichierProtege findById(int id) {

        String sql =
                "SELECT f.id, f.nom, f.droits, u.login " +
                "FROM fichiers f " +
                "JOIN users u ON f.proprietaire_id = u.id " +
                "WHERE f.id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {
                return convertirResultat(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public FichierProtege findByNom(String nom) {

        String sql =
                "SELECT f.id, f.nom, f.droits, u.login " +
                "FROM fichiers f " +
                "JOIN users u ON f.proprietaire_id = u.id " +
                "WHERE f.nom = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, nom);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {
                return convertirResultat(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<FichierProtege> findAll() {

        List<FichierProtege> fichiers =
                new ArrayList<>();

        String sql =
                "SELECT f.id, f.nom, f.droits, u.login " +
                "FROM fichiers f " +
                "JOIN users u ON f.proprietaire_id = u.id";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                FichierProtege fichier =
                        convertirResultat(result);

                fichiers.add(fichier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fichiers;
    }

    public List<FichierProtege> findByProprietaire(
            int userId) {

        List<FichierProtege> fichiers =
                new ArrayList<>();

        String sql =
                "SELECT f.id, f.nom, f.droits, u.login " +
                "FROM fichiers f " +
                "JOIN users u ON f.proprietaire_id = u.id " +
                "WHERE f.proprietaire_id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet result =
                    statement.executeQuery();

            while (result.next()) {

                FichierProtege fichier =
                        convertirResultat(result);

                fichiers.add(fichier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fichiers;
    }

    public void updateDroits(int id, String droits) {

        String sql =
                "UPDATE fichiers SET droits = ? WHERE id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, droits);
            statement.setInt(2, id);

            int lignesModifiees =
                    statement.executeUpdate();

            if (lignesModifiees > 0) {
                System.out.println(
                        "Droits modifiés avec succès."
                );
            } else {
                System.out.println(
                        "Fichier introuvable."
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {

        String sql =
                "DELETE FROM fichiers WHERE id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int lignesSupprimees =
                    statement.executeUpdate();

            if (lignesSupprimees > 0) {
                System.out.println(
                        "Fichier supprimé avec succès."
                );
            } else {
                System.out.println(
                        "Fichier introuvable."
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}