package ma.youcode.lineperm.access;

import ma.youcode.lineperm.model.FichierProtege;

public class ControleAcces {

    public static boolean estAutorise(
            String login,
            FichierProtege fichier,
            char droit) {

        if (login.equals(fichier.getProprietaire())) {

            if (droit == 'r') {
                return fichier.isOwnerRead();
            }

            if (droit == 'w') {
                return fichier.isOwnerWrite();
            }

            if (droit == 'd') {
                return fichier.isOwnerDelete();
            }
        }

        else {

            if (droit == 'r') {
                return fichier.isOtherRead();
            }

            if (droit == 'w') {
                return fichier.isOtherWrite();
            }

            if (droit == 'd') {
                return fichier.isOtherDelete();
            }
        }

        return false;
    }
}