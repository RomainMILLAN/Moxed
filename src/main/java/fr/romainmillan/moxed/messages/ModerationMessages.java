package fr.romainmillan.moxed.messages;

public enum ModerationMessages {
    FREEZE_PLAYER(MoxedMessage.PREFIX_NORMAL.getMessage() + "Vous venez de §9Freeze §fle joueur "),
    UNFREEZE_PLAYER(MoxedMessage.PREFIX_NORMAL.getMessage() + "Vous venez de §9Defreeze §fle joueur "),
    PLAYER_FREEZE(MoxedMessage.PREFIX_ERROR.getMessage() + "Vous êtes §4Freeze"),
    WEATHER_CLEAR(MoxedMessage.PREFIX_NORMAL.getMessage() + "Vous venez d'enlever la §9Pluie"),
    KICK_PERSON(MoxedMessage.PREFIX_NORMAL.getMessage() + "Vous venez de kick "),
    BAN_PERSON(MoxedMessage.PREFIX_NORMAL.getMessage() + "Vous venez de bannir "),
    MUTE_PERSON(MoxedMessage.PREFIX_NORMAL.getMessage() + "Vous venez de §9Mute §f"),
    UNMUTE_PERSON(MoxedMessage.PREFIX_NORMAL.getMessage() + "Vous venez de §9UnMute §f"),
    PEOPLE_MUTE(MoxedMessage.PREFIX_NORMAL.getMessage() + "Vous venez d'être §9Mute §fpar "),
    PEOPLE_UNMUTE(MoxedMessage.PREFIX_NORMAL.getMessage() + "Vous venez d'être §9UnMute §fpar "),
    CLEAR_SUCCESS(MoxedMessage.PREFIX_NORMAL.getMessage() + "Vous venez de §9Clear §fle chat"),
    MUTE(MoxedMessage.PREFIX_ERROR.getMessage() + "Vous êtes §4Mute"),
    NO_WARNS(MoxedMessage.PREFIX_NORMAL.getMessage() + "La personne sélectionné n'as aucun §9Warns"),
    TICKET_CREATE_SUCESS(MoxedMessage.PREFIX_NORMAL.getMessage() + "Votre ticket à était §9Crée"),
    TICKET_DELETE_SUCESS(MoxedMessage.PREFIX_NORMAL.getMessage() + "Le ticket à était §9Supprimé"),
    MAINTENANCE_ON(MoxedMessage.PREFIX_NORMAL.getMessage() + "La maintenance à était §9Activé"),
    MAINTENANCE_OFF(MoxedMessage.PREFIX_NORMAL.getMessage() + "La mainteancen à était §9Désactivé"),
    MAINTENANCE_ON_IMPOSSIBLE(MoxedMessage.PREFIX_ERROR.getMessage() + "La maintenance est déjà §4Activé"),
    MAINTENANCE_OFF_IMPOSSIBLE(MoxedMessage.PREFIX_ERROR.getMessage() + "La maintenance est déjà §4Désactivé"),
    MAINTENANCE_ACTIVATE(MoxedMessage.PREFIX_ERROR.getMessage() + "Une maintenance est §4Activé");


    private String messages;

    ModerationMessages(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }
}
