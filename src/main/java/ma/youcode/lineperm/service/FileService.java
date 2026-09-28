package ma.youcode.lineperm.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import ma.youcode.lineperm.access.ControleAcces;
import ma.youcode.lineperm.dao.FichierDAO;
import ma.youcode.lineperm.dao.LogDAO;
import ma.youcode.lineperm.dao.UserDAO;
import ma.youcode.lineperm.model.FichierProtege;
import ma.youcode.lineperm.model.LogEntry;
import ma.youcode.lineperm.model.User;

public class FileService {

    private final FichierDAO fichierDAO;
    private final LogDAO logDAO;
    private final UserDAO userDAO;

    private final Path dataPath = Path.of("data");

    public FileService() {
        fichierDAO = new FichierDAO();
        logDAO = new LogDAO();
        userDAO = new UserDAO();
    }

    public void charger() {
    }

    public List<FichierProtege> lister() {
        return fichierDAO.findAll();
    }

    private boolean nomValide(String nom) {

        if (nom == null || nom.isBlank()) {
            return false;
        }

        return !nom.contains("/")
                && !nom.contains("\\")
                && !nom.contains(";");
    }

    public boolean creer(
            String nom,
            String proprietaire) {

        if (!nomValide(nom)) {
            return false;
        }

        if (fichierDAO.findByNom(nom) != null) {
            return false;
        }

        try {

            Files.createDirectories(dataPath);

            Path contenuPath =
                    dataPath.resolve(nom);

            Files.writeString(
                    contenuPath,
                    ""
            );

            FichierProtege fichier =
                    new FichierProtege(
                            nom,
                            proprietaire
                    );

            fichierDAO.save(fichier);

            FichierProtege fichierCree =
                    fichierDAO.findByNom(nom);

            if (fichierCree == null) {
                return false;
            }

            enregistrerLog(
                    proprietaire,
                    fichierCree.getId(),
                    "CREATE",
                    "SUCCESS"
            );

            return true;

        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean existe(String nom) {
        return fichierDAO.findByNom(nom) != null;
    }

    public String lire(
            String login,
            String nom) {

        FichierProtege fichier =
                fichierDAO.findByNom(nom);

        if (fichier == null) {
            return null;
        }

        if (!ControleAcces.estAutorise(
                login,
                fichier,
                'r')) {

            enregistrerLog(
                    login,
                    fichier.getId(),
                    "READ",
                    "REFUSED"
            );

            return null;
        }

        try {

            Path contenuPath =
                    dataPath.resolve(nom);

            if (!Files.exists(contenuPath)) {
                return "";
            }

            String contenu =
                    Files.readString(contenuPath);

            enregistrerLog(
                    login,
                    fichier.getId(),
                    "READ",
                    "SUCCESS"
            );

            return contenu;

        } catch (IOException e) {

            return null;
        }
    }

    public boolean ecrire(
            String login,
            String nom,
            String contenu) {

        FichierProtege fichier =
                fichierDAO.findByNom(nom);

        if (fichier == null) {
            return false;
        }

        if (!ControleAcces.estAutorise(
                login,
                fichier,
                'w')) {

            enregistrerLog(
                    login,
                    fichier.getId(),
                    "WRITE",
                    "REFUSED"
            );

            return false;
        }

        try {

            Files.createDirectories(dataPath);

            Path contenuPath =
                    dataPath.resolve(nom);

            Files.writeString(
                    contenuPath,
                    contenu
            );

            enregistrerLog(
                    login,
                    fichier.getId(),
                    "WRITE",
                    "SUCCESS"
            );

            return true;

        } catch (IOException e) {

            return false;
        }
    }

    public boolean peutEcrire(
            String login,
            String nom) {

        FichierProtege fichier =
                fichierDAO.findByNom(nom);

        if (fichier == null) {
            return false;
        }

        boolean autorise =
                ControleAcces.estAutorise(
                        login,
                        fichier,
                        'w'
                );

        if (!autorise) {

            enregistrerLog(
                    login,
                    fichier.getId(),
                    "WRITE",
                    "REFUSED"
            );
        }

        return autorise;
    }

    public boolean chmod(
            String login,
            char droit,
            boolean ajouter,
            String nom) {

        FichierProtege fichier =
                fichierDAO.findByNom(nom);

        if (fichier == null) {
            return false;
        }

        if (!login.equals(
                fichier.getProprietaire())) {

            enregistrerLog(
                    login,
                    fichier.getId(),
                    "CHMOD",
                    "REFUSED"
            );

            return false;
        }

        if (droit == 'r') {

            fichier.setOtherRead(ajouter);

        } else if (droit == 'w') {

            fichier.setOtherWrite(ajouter);

        } else if (droit == 'd') {

            fichier.setOtherDelete(ajouter);

        } else {

            return false;
        }

        String droits =
                construireDroits(fichier);

        fichierDAO.updateDroits(
                fichier.getId(),
                droits
        );

        enregistrerLog(
                login,
                fichier.getId(),
                "CHMOD",
                "SUCCESS"
        );

        return true;
    }

    private String construireDroits(
            FichierProtege fichier) {

        String owner =
                ""
                + (fichier.isOwnerRead()
                    ? "r" : "-")
                + (fichier.isOwnerWrite()
                    ? "w" : "-")
                + (fichier.isOwnerDelete()
                    ? "d" : "-");

        String other =
                ""
                + (fichier.isOtherRead()
                    ? "r" : "-")
                + (fichier.isOtherWrite()
                    ? "w" : "-")
                + (fichier.isOtherDelete()
                    ? "d" : "-");

        return owner + "|" + other;
    }

    private void enregistrerLog(
            String login,
            int fichierId,
            String action,
            String status) {

        User user =
                userDAO.findByUsername(login);

        if (user == null) {
            return;
        }

        LogEntry log =
                new LogEntry(
                        0,
                        user.getId(),
                        fichierId,
                        action,
                        status,
                        LocalDateTime.now().toString()
                );

        logDAO.save(log);
    }
}