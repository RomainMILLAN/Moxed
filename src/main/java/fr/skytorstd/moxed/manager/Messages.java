package fr.skytorstd.moxed.manager;

public enum Messages {

    PREFIX_NORMAL("§f► "),
    PREFIX_NOTAPLAYER("§4► §cIl faut être un joueur pour pouvoir effectuée cette commande."),
    PREFIX_ERRORCMD("§4► §cMauvaise commande, merci d'utiliser: §f"),
    PREFIX_ERRROR("§4► §c"),
    PREFIX_ERRORPERM("§4► §cTu n'as pas la permission pour effectuer cette commande"),

    //ERROR MESSAGE
    ERRORMESSAGE_PLAYER_NOT_CONNECTED(PREFIX_ERRROR.getMessage() + "Veuilliez mettre un joueur en ligne sur le serveur !"),

    //MAINTENANCE
    MESSAGE_ENTREE_EN_MAINTENANCE(PREFIX_ERRROR.getMessage() + "Le serveur rentre en MAINTENANCE");

    private final String message;

    Messages(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

}
