package fr.romainmillan.moxed.messages;

public enum MoxedMessage {

    //PREFIX
    PREFIX_NORMAL("§f► "),
    PREFIX_ERROR("§4► §c"),
    PREFIX_STAFF("§6► "),

    //ERROR MESSAGES,
    EM_ERRORCMD(PREFIX_ERROR.getMessage() + "Mauvaise commande !"),
    EM_ERRORCMD_WITH_COMMAND_TO_USE(PREFIX_ERROR.getMessage() + "Mauvaise commande, merci d'utiliser §f"),
    EM_ERRORPERM(PREFIX_ERROR.getMessage() + "Tu n'as pas la permission pour effectuer cette action"),
    EM_PLAYER_NOT_CONNECTED(PREFIX_ERROR.getMessage() + "Veuilliez mettre un joueur en ligne"),
    EM_NOT_PLAYER(PREFIX_ERROR.getMessage() + "Vous devez être un joueur pour effectué cette commande");

    private final String message;

    MoxedMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
