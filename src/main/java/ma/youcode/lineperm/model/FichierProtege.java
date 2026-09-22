package ma.youcode.lineperm.model;

public class FichierProtege {
    private String nom;
    private String proprietaire;

    private boolean ownerRead;
    private boolean ownerWrite;
    private boolean ownerDelete;

    private boolean otherRead;
    private boolean otherWrite;
    private boolean otherDelete;


    public FichierProtege(String nom,String proprietaire,boolean ownerRead,boolean ownerWrite,boolean  ownerDelete
                          ,boolean otherRead,boolean otherWrite,boolean otherDelete ){
                            this.nom=nom;
                            this.proprietaire=proprietaire;
                            this.ownerRead=ownerRead;
                            this.ownerWrite=ownerWrite;
                            this.ownerDelete=ownerDelete;
                            this.otherRead=otherRead;
                            this.otherWrite=otherWrite;
                            this.otherDelete=otherDelete;


                          }

    public FichierProtege(String nom,String proprietaire){
        this(nom,proprietaire,
            true,
            true,
            true,
            false,
            false,
            false);
    } 
   public String getNom() {
    return nom;
}

public String getProprietaire() {
    return proprietaire;
}

public boolean isOwnerRead() {
    return ownerRead;
}

public boolean isOwnerWrite() {
    return ownerWrite;
}

public boolean isOwnerDelete() {
    return ownerDelete;
}

public boolean isOtherRead() {
    return otherRead;
}

public boolean isOtherWrite() {
    return otherWrite;
}

public boolean isOtherDelete() {
    return otherDelete;
}

public void setOtherRead(boolean otherRead) {
    this.otherRead = otherRead;
}
public void setOtherWrite(boolean  otherWrite){
    this.otherWrite = otherWrite;
}
    
   public void setOtherDelete(boolean  otherDelete){
    this.otherDelete = otherDelete;
   }
    
}
