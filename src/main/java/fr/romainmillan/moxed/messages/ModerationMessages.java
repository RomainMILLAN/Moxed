package fr.romainmillan.moxed.messages;

public enum ModerationMessages {
    FREEZE_PLAYER(MoxedMessage.PREFIX_NORMAL.getMessage() + "Vous venez de §9Freeze §fle joueur "),
    UNFREEZE_PLAYER(MoxedMessage.PREFIX_NORMAL.getMessage() + "Vous venez de §9Defreeze §fle joueur "),
    PLAYER_FREEZE(MoxedMessage.PREFIX_ERROR.getMessage() + "Vous êtes §4Freeze");


    private String messages;

    ModerationMessages(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }
}
