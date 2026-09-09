package ma.youcode.lineperm.model;

public class Fichier{
    private final String nom;
    private final String proprietaire;
    private final String permissionsUser;
    private final String permissionsOthers;
        public Fichier (String nom,String proprietaire ,String permissionsUser, String permissionsOthers){
         this.nom = nom;
        this.proprietaire = proprietaire;
        this.permissionsUser = permissionsUser;
        this.permissionsOthers = permissionsOthers;
        }

         public String getNom() {
        return nom;
    }
      public String getProprietaire() {
        return proprietaire;
    }

    public String getPermissionsUser() {
        return permissionsUser;
    }

    public String getPermissionsOthers() {
        return permissionsOthers;
    }
    
}