package fr.skytorstd.moxed.manager;

public enum Messages {

    PREFIX_NORMAL("§f► "),
    PREFIX_NOTAPLAYER("§4► §cIl faut être un joueur pour pouvoir effectuée cette commande."),
    PREFIX_ERRORCMD("§4► §cMauvaise commande, merci d'utiliser: §f"),
    PREFIX_ERRROR("§4► §c"),
    PREFIX_ERRORPERM("§4► §cTu n'as pas la permission pour effectuer cette commande");

    private final String message;

    Messages(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

}
